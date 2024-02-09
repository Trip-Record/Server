package com.triprecord.triprecord.record.service;


import com.triprecord.triprecord.global.exception.ErrorCode;
import com.triprecord.triprecord.global.exception.TripRecordException;
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

    public RecordComment getRecordCommentOrException(Long recordId) {
        return recordCommentRepository.findById(recordId).orElseThrow(
                ()->new TripRecordException(ErrorCode.RECORD_COMMENT_NOT_FOUND));
    }
}
