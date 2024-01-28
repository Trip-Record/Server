package com.triprecord.triprecord.record.service;


import com.triprecord.triprecord.record.entity.Record;
import com.triprecord.triprecord.record.repository.RecordCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecordCommentService {

    private final RecordCommentRepository recordCommentRepository;


    public Long getRecordCommentCount(Record record){
        return recordCommentRepository.countByCommentedRecord(record);
    }
}
