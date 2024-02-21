package com.triprecord.triprecord.record.service;

import com.triprecord.triprecord.global.exception.ErrorCode;
import com.triprecord.triprecord.global.exception.TripRecordException;
import com.triprecord.triprecord.global.util.S3Service;
import com.triprecord.triprecord.record.dto.RecordImageData;
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


    public void checkImageSizeValid(Record record, List<Long> deleteRequestImages, List<MultipartFile> addRequestImages){
        long existingSize = recordImageRepository.countByLinkedRecord(record);
        long deleteSize = (deleteRequestImages==null)?0:deleteRequestImages.size();
        long addSize = (addRequestImages==null)?0:addRequestImages.size();
        long changingSize = existingSize - deleteSize + addSize;
        if (changingSize > MAX_IMAGE_SIZE) {
            throw new TripRecordException(ErrorCode.INVALID_RECORD_IMAGE_SIZE);
        }
    }

    @Transactional
    public void createRecordImages(Record record, List<MultipartFile> addRequestImages){
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

    @Transactional(readOnly = true)
    public List<RecordImageData> findRecordImageData(Record record) {
        List<RecordImage> images =  recordImageRepository.findAllByLinkedRecord(record);
        return images.stream().map(RecordImageData::fromImage).toList();
    }

    @Transactional
    public void deleteRecordImages(List<Long> deleteRequestImageIds){
        if(deleteRequestImageIds==null || deleteRequestImageIds.isEmpty()) return;
        int S3_PATH_LENGTH = s3Service.getFileURLFromS3(null).toString().length();
        for(Long imageId : deleteRequestImageIds){
            RecordImage recordImage = getRecordImageOrException(imageId);
            s3Service.deleteFileFromS3(recordImage.getRecordImgUrl().substring(S3_PATH_LENGTH));
            recordImageRepository.delete(recordImage);
        }
    }

    private String uploadToS3AndGetURL(MultipartFile image){
        String uploadName = getNameAddRandomUUID(image.getOriginalFilename());
        s3Service.uploadFileToS3(image, uploadName);
        return s3Service.getFileURLFromS3(uploadName).toString();
    }


    private RecordImage getRecordImageOrException(Long recordImageId){
        return recordImageRepository.findById(recordImageId).orElseThrow(()->
                new TripRecordException(ErrorCode.IMAGE_NOT_FOUND));
    }

    public void deleteS3RecordImage(Record record){
        List<RecordImage> recordImages = recordImageRepository.findAllByLinkedRecord(record);
        if(recordImages.isEmpty()) return;
        String basicS3Path = s3Service.getFileURLFromS3(null).toString();
        for (RecordImage image : recordImages){
            String imageName = image.getRecordImgUrl().substring(basicS3Path.length());
            s3Service.deleteFileFromS3(imageName);
        }
    }

    private String getNameAddRandomUUID(String originName) {
        String randomUUID = UUID.randomUUID().toString();
        return randomUUID+originName;
    }

}
