package com.triprecord.triprecord.record.service;


import com.triprecord.triprecord.global.exception.ErrorCode;
import com.triprecord.triprecord.global.exception.TripRecordException;
import com.triprecord.triprecord.record.controller.response.RecordCommentPage;
import com.triprecord.triprecord.record.dto.RecordCommentData;
import com.triprecord.triprecord.record.entity.Record;
import com.triprecord.triprecord.record.entity.RecordComment;
import com.triprecord.triprecord.record.repository.RecordCommentRepository;
import com.triprecord.triprecord.user.entity.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecordCommentService {

    private final RecordCommentRepository recordCommentRepository;

    public Long getRecordCommentCount(Record record){
        return recordCommentRepository.countByCommentedRecord(record);
    }

    public RecordComment getRecordCommentOrException(Long recordId) {
        return recordCommentRepository.findById(recordId).orElseThrow(
                ()->new TripRecordException(ErrorCode.RECORD_COMMENT_NOT_FOUND));
    }

    public RecordCommentPage getRecordComments(Optional<Long> userId, Record record, Pageable pageable) {
        Page<RecordComment> comments = recordCommentRepository.findAllByCommentedRecord(record, pageable);
        List<RecordCommentData> commentData = new ArrayList<>();
        for(RecordComment comment : comments) {
            boolean isUserCreated = userId.isPresent() && userId.get().equals(comment.getCommentedUser().getUserId());
            commentData.add(RecordCommentData.fromEntity(comment, isUserCreated));
        }

        return RecordCommentPage.builder()
                .totalPages(comments.getTotalPages())
                .pageNumber(comments.getNumber())
                .recordComments(commentData)
                .build();
    }

    public void createRecordComment(User user, Record record, String content) {
        RecordComment comment = RecordComment.builder()
                .user(user)
                .record(record)
                .commentContent(content)
                .build();
        recordCommentRepository.save(comment);
    }
  
    public void updateRecordComment(RecordComment comment, String newContent) {
        if(comment.getCommentContent().equals(newContent)) {
            throw new TripRecordException(ErrorCode.RECORD_COMMENT_DUPLICATE);
        }
        comment.updateContent(newContent);
    }
  
    public void deleteRecordComment(RecordComment comment) {
        recordCommentRepository.delete(comment);
    }
  
}
