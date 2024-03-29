package com.triprecord.triprecord.record.service;


import com.triprecord.triprecord.global.exception.ErrorCode;
import com.triprecord.triprecord.global.exception.TripRecordException;
import com.triprecord.triprecord.location.dto.PlaceBasicData;
import com.triprecord.triprecord.record.controller.request.RecordModifyRequest;
import com.triprecord.triprecord.record.controller.response.RecordCommentPage;
import com.triprecord.triprecord.record.controller.response.RecordPageResponse;
import com.triprecord.triprecord.record.controller.response.RecordResponse;
import com.triprecord.triprecord.record.dto.RecordImageData;
import com.triprecord.triprecord.record.dto.RecordUpdateData;
import com.triprecord.triprecord.record.entity.Record;
import com.triprecord.triprecord.record.entity.RecordComment;
import com.triprecord.triprecord.record.repository.RecordRepository;
import com.triprecord.triprecord.record.controller.request.RecordCreateRequest;
import com.triprecord.triprecord.user.service.UserService;
import com.triprecord.triprecord.user.entity.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
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
    private final RecordCommentService recordCommentService;
    private final RecordLikeService recordLikeService;



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
        recordPlaceService.createRecordPlaces(record, request.placeIds());
        recordImageService.createRecordImages(record, request.recordImages());
    }

    @Transactional(readOnly = true)
    public RecordPageResponse getRecordPage(Optional<Long> userId, Pageable pageable) {
        Page<Record> records = recordRepository.findAllOrderById(pageable);
        List<RecordResponse> recordResponses = new ArrayList<>();
        for(Record record : records.getContent()) {
            recordResponses.add(getRecordResponseData(userId, record.getRecordId()));
        }
        return RecordPageResponse.builder()
                .totalPages(records.getTotalPages())
                .pageNumber(records.getNumber())
                .recordList(recordResponses)
                .build();
    }

    @Transactional(readOnly = true)
    public RecordResponse getRecordResponseData(Optional<Long> userId, Long recordId) {
        Record record = getRecordOrException(recordId);

        List<PlaceBasicData> recordPlaceData = recordPlaceService.getRecordPlaceBasicData(record);
        List<RecordImageData> recordImageData = recordImageService.findRecordImageData(record);

        boolean isUserCreated = false; boolean isUserLiked = false;
        if(userId.isPresent()) {
            isUserCreated = Objects.equals(record.getCreatedUser().getUserId(), userId.get());
            isUserLiked = recordLikeService.findUserLikedRecord(record, userService.getUserOrException(userId.get()));
        }

        Long likeCount = recordLikeService.getRecordLikeCount(record);
        Long commentCount = recordCommentService.getRecordCommentCount(record);

        return RecordResponse.fromRecordData(record, recordPlaceData, recordImageData, isUserCreated, isUserLiked, likeCount, commentCount);
    }


    @Transactional
    public void deleteRecord(Long userId, Long recordId){
        User user = userService.getUserOrException(userId);
        Record record = getRecordOrException(recordId);
        checkSameUser(record.getCreatedUser(), user);
        recordImageService.deleteS3RecordImage(record);
        recordRepository.delete(record);
    }

    @Transactional
    public void modifyRecord(Long userId, Long recordId, RecordModifyRequest request){
        User user = userService.getUserOrException(userId);
        Record record = getRecordOrException(recordId);
        checkSameUser(record.getCreatedUser(), user);

        RecordUpdateData recordUpdateData = RecordUpdateData.fromRequest(request);
        checkDateValid(recordUpdateData.startDate(), recordUpdateData.endDate());
        record.updateRecord(recordUpdateData);

        modifyPlace(record, request.deletePlaceIds(), request.addPlaceIds());
        modifyImage(record, request.deleteImages(), request.addImages());
    }

    @Transactional(readOnly = true)
    public RecordCommentPage getRecordComments(Optional<Long> userId, Long recordId, Pageable pageable) {
        Record record = getRecordOrException(recordId);

        return recordCommentService.getRecordComments(userId, record, pageable);
    }
  
    @Transactional
    public void postCommentToRecord(Long userId, Long recordId, String content) {
        User user = userService.getUserOrException(userId);
        Record record = getRecordOrException(recordId);
        recordCommentService.createRecordComment(user, record, content);
    }


    @Transactional
    public void modifyRecordComment(Long userId, Long recordId, String content) {
        User user = userService.getUserOrException(userId);
        RecordComment comment = recordCommentService.getRecordCommentOrException(recordId);
        checkSameUser(comment.getCommentedUser(), user);

        recordCommentService.updateRecordComment(comment, content);
    }

    @Transactional
    public void deleteCommentFromRecord(Long userId, Long recordCommentId) {
        User user = userService.getUserOrException(userId);

        RecordComment comment = recordCommentService.getRecordCommentOrException(recordCommentId);
        checkSameUser(comment.getCommentedUser(), user);

        recordCommentService.deleteRecordComment(comment);
    }

    public void postLikeToRecord(Long userId, Long recordId) {
        User user  = userService.getUserOrException(userId);
        Record record = getRecordOrException(recordId);
        recordLikeService.createRecordLike(user, record);
    }

    public void cancelLikeToRecord(Long userId, Long recordId) {
        User user  = userService.getUserOrException(userId);
        Record record = getRecordOrException(recordId);
        recordLikeService.deleteRecordLike(user, record);
    }

    private void checkSameUser(User createdUser, User user){
        if(createdUser != user) throw new TripRecordException(ErrorCode.INVALID_PERMISSION);
    }


    private void checkDateValid(LocalDate startDate, LocalDate endDate){
        if(endDate.isBefore(startDate)) throw new TripRecordException(ErrorCode.INVALID_DATE);
    }


    private void modifyPlace(Record record, List<Long> deletePlaceIds, List<Long> addPlaceIds){
        if(deletePlaceIds==null && addPlaceIds==null) return;
        recordPlaceService.checkPlaceSizeValid(record, deletePlaceIds, addPlaceIds);
        recordPlaceService.deleteRecordPlaces(record, deletePlaceIds);
        recordPlaceService.createRecordPlaces(record, addPlaceIds);
    }

    private void modifyImage(Record record, List<Long> deleteImages, List<MultipartFile> addImages){
        if(deleteImages==null && addImages==null) return;
        recordImageService.checkImageSizeValid(record, deleteImages, addImages);
        recordImageService.deleteRecordImages(deleteImages);
        recordImageService.createRecordImages(record, addImages);
    }

    private Record getRecordOrException(Long recordId){
        return recordRepository.findByRecordId(recordId).orElseThrow(()->
                new TripRecordException(ErrorCode.RECORD_NOT_FOUND));
    }

}
