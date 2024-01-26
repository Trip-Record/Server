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
import java.time.LocalDate;
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
        checkDateValid(request.startDate(), request.endDate());
        Record record = Record.builder()
                .recordTitle(request.recordTitle())
                .recordContent(request.recordContent())
                .tripStartDate(request.startDate())
                .tripEndDate(request.endDate())
                .createdUser(createdUser)
                .build();
        recordRepository.save(record);
        recordPlaceService.uploadRecordPlaces(record, request.placeIds());
        recordImageService.uploadRecordImages(record, request.recordImages());
    }


    private void checkDateValid(LocalDate startDate, LocalDate endDate){
        if(endDate.isBefore(startDate)) throw new TripRecordException(ErrorCode.INVALID_DATE);
    }


    private void modifyPlace(Record record, List<Long> deletePlaceIds, List<Long> addPlaceIds){
        if(deletePlaceIds==null && addPlaceIds==null) return;
        recordPlaceService.checkPlaceSizeValid(record, deletePlaceIds, addPlaceIds);
        recordPlaceService.deleteRecordPlaces(record, deletePlaceIds);
        recordPlaceService.uploadRecordPlaces(record, addPlaceIds);
    }

    private void modifyImage(Record record, List<String> deleteImages, List<MultipartFile> addImages){
        if(deleteImages==null && addImages==null) return;
        recordImageService.checkImageSizeValid(record, deleteImages, addImages);
        recordImageService.deleteRecordImages(record, deleteImages);
        recordImageService.uploadRecordImages(record, addImages);
    }

    }

}
