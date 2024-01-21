package com.triprecord.triprecord.record.service;

import com.triprecord.triprecord.global.exception.ErrorCode;
import com.triprecord.triprecord.global.exception.TripRecordException;
import com.triprecord.triprecord.record.entity.Record;
import com.triprecord.triprecord.record.entity.RecordPlace;
import com.triprecord.triprecord.record.repository.RecordPlaceRepository;
import com.triprecord.triprecord.record.repository.RecordRepository;
import com.triprecord.triprecord.record.controller.request.RecordCreateRequest;
import com.triprecord.triprecord.user.entity.User;
import com.triprecord.triprecord.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecordService {

    private final UserRepository userRepository;
    private final RecordRepository recordRepository;
    private final RecordImageService recordImageService;
    private final RecordPlaceService recordPlaceService;

    @Transactional
    public void createRecord(Long userId, RecordCreateRequest request){
        User createdUser = getUserOrException(userId);
        Record record = Record.builder()
                .recordTitle(request.recordTitle())
                .recordContent(request.recordContent())
                .tripStartDate(request.startDate())
                .tripEndDate(request.endDate())
                .createdUser(createdUser)
                .build();
        recordRepository.save(record);
        for(Long placeId : request.placeIds()){
            recordPlaceService.uploadRecordPlace(record, placeId);
        }
        if(request.recordImages()==null) return;
        for(MultipartFile image : request.recordImages()){
            recordImageService.uploadRecordImage(image, record);
        }
    }

    private User getUserOrException(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new TripRecordException(ErrorCode.USER_NOT_FOUND));
    }

}
