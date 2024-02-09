package com.triprecord.triprecord.record.service;


import com.triprecord.triprecord.record.entity.Record;
import com.triprecord.triprecord.record.entity.RecordComment;
import com.triprecord.triprecord.record.repository.RecordCommentRepository;
import com.triprecord.triprecord.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecordCommentService {

    private final RecordCommentRepository recordCommentRepository;

    public Long getRecordCommentCount(Record record){
        return recordCommentRepository.countByCommentedRecord(record);
    }

    public void createRecordComment(User user, Record record, String content) {
        RecordComment comment = RecordComment.builder()
                .user(user)
                .record(record)
                .commentContent(content)
                .build();
        recordCommentRepository.save(comment);
    }
}
