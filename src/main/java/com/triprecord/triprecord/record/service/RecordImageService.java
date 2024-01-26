package com.triprecord.triprecord.record.service;

import com.triprecord.triprecord.global.exception.ErrorCode;
import com.triprecord.triprecord.global.exception.TripRecordException;
import com.triprecord.triprecord.global.util.S3Service;
import com.triprecord.triprecord.record.entity.Record;
import com.triprecord.triprecord.record.entity.RecordImage;
import com.triprecord.triprecord.record.repository.RecordImageRepository;
import java.net.URL;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecordImageService {

    private final S3Service s3Service;
    private final RecordImageRepository recordImageRepository;
    private final long MAX_IMAGE_SIZE = 10;


    public void checkImageSizeValid(Record record, List<String> deleteRequestImages, List<MultipartFile> addRequestImages){
        long existingSize = recordImageRepository.countByLinkedRecord(record);
        long deleteSize = (deleteRequestImages==null)?0:deleteRequestImages.size();
        long addSize = (addRequestImages==null)?0:addRequestImages.size();
        long changingSize = existingSize - deleteSize + addSize;
        if (changingSize > MAX_IMAGE_SIZE) {
            throw new TripRecordException(ErrorCode.INVALID_RECORD_IMAGE_SIZE);
        }
    }

    @Transactional
    public void uploadRecordImages(Record record, List<MultipartFile> addRequestImages){
        if(addRequestImages==null || addRequestImages.isEmpty()) return;
        for(MultipartFile image : addRequestImages){
            String imageURL = uploadToS3AndGetURL(image);
            RecordImage recordImage = RecordImage.builder()
                    .linkedRecord(record)
                    .imageURL(imageURL)
                    .build();
            recordImageRepository.save(recordImage);
        }
    }

    @Transactional
    public void deleteRecordImages(Record record, List<String> deleteRequestImageURLs){
        if(deleteRequestImageURLs==null || deleteRequestImageURLs.isEmpty()) return;
        int S3_PATH_LENGTH = s3Service.getFileURLFromS3(null).toString().length();
        for(String imageURL : deleteRequestImageURLs){
            RecordImage recordImage = getRecordImageOrException(record, imageURL);
            recordImageRepository.delete(recordImage);
            s3Service.deleteFileFromS3(imageURL.substring(S3_PATH_LENGTH));
        }
    }

    private String uploadToS3AndGetURL(MultipartFile image){
        String uploadName = getNameAddRandomUUID(image.getOriginalFilename());
        s3Service.uploadFileToS3(image, uploadName);
        return s3Service.getFileURLFromS3(uploadName).toString();
    }


    private RecordImage getRecordImageOrException(Record record, String imageURL){
        return recordImageRepository.findAllByLinkedRecordAndRecordImgUrl(record, imageURL).orElseThrow(()->
                new TripRecordException(ErrorCode.IMAGE_NOT_FOUND));
    }


    private String getNameAddRandomUUID(String originName) {
        String randomUUID = UUID.randomUUID().toString();
        return randomUUID+originName;
    }

}
