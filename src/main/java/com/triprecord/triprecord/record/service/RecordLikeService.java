package com.triprecord.triprecord.record.service;


import com.triprecord.triprecord.record.entity.Record;
import com.triprecord.triprecord.record.repository.RecordLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecordLikeService {

    private final RecordLikeRepository recordLikeRepository;

    public Long getRecordLikeCount(Record record){
        return recordLikeRepository.countByLikedRecord(record);
    }
}
