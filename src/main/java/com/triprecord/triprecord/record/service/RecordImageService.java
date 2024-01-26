package com.triprecord.triprecord.record.service;

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

    @Transactional
    public void uploadRecordImage(MultipartFile image, Record record){
        String uploadName = getNameAddRandomUUID(image.getOriginalFilename());
        s3Service.uploadFileToS3(image, uploadName);
        String imageURL = s3Service.getFileURLFromS3(uploadName).toString();
        RecordImage recordImage = RecordImage.builder()
                .linkedRecord(record)
                .imageURL(imageURL)
                .build();
        recordImageRepository.save(recordImage);
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
