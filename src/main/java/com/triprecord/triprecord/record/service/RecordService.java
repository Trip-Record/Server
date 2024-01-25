package com.triprecord.triprecord.record.service;

import com.triprecord.triprecord.global.exception.ErrorCode;
import com.triprecord.triprecord.global.exception.TripRecordException;
import com.triprecord.triprecord.record.entity.Record;
import com.triprecord.triprecord.record.entity.RecordPlace;
import com.triprecord.triprecord.record.repository.RecordPlaceRepository;
import com.triprecord.triprecord.record.repository.RecordRepository;
import com.triprecord.triprecord.record.controller.request.RecordCreateRequest;
import com.triprecord.triprecord.user.UserService;
import com.triprecord.triprecord.user.entity.User;
import com.triprecord.triprecord.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecordService {

    private final UserService userService;
    private final RecordRepository recordRepository;
    private final RecordImageService recordImageService;
    private final RecordPlaceService recordPlaceService;

    @Transactional
    public void createRecord(Long userId, RecordCreateRequest request){
        User createdUser = userService.getUserOrException(userId);
        Record record = Record.builder()
                .recordTitle(request.recordTitle())
                .recordContent(request.recordContent())
                .tripStartDate(request.startDate())
                .tripEndDate(request.endDate())
                .createdUser(createdUser)
                .build();
        recordRepository.save(record);
        uploadPlace(request.placeIds(), record);
        uploadImage(request.recordImages(), record);
    }

    private void uploadPlace(List<Long> placeIds, Record linkedRecord){
        for(Long placeId : placeIds){
            recordPlaceService.uploadRecordPlace(linkedRecord, placeId);
        }
    }

    private void uploadImage(List<MultipartFile> images, Record linkedRecord){
        if(images==null) return;
        for(MultipartFile image : images){
            recordImageService.uploadRecordImage(image, linkedRecord);
        }
    }

}
