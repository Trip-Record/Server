package com.triprecord.triprecord.record.service;


import com.triprecord.triprecord.global.exception.ErrorCode;
import com.triprecord.triprecord.global.exception.TripRecordException;
import com.triprecord.triprecord.record.entity.Record;
import com.triprecord.triprecord.record.entity.RecordLike;
import com.triprecord.triprecord.record.repository.RecordLikeRepository;
import com.triprecord.triprecord.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class RecordLikeService {

    private final RecordLikeRepository recordLikeRepository;


    public Long getRecordLikeCount(Record record){
        return recordLikeRepository.countByLikedRecord(record);
    }

    @Transactional
    public void createRecordLike(User user, Record record){
        recordLikeRepository.findByLikedRecordAndLikedUser(record, user).ifPresent(it->{
            throw new TripRecordException(ErrorCode.RECORD_ALREADY_LIKED);
        });
        RecordLike recordLike = RecordLike.builder()
                .likedUser(user)
                .likedRecord(record)
                .build();
        recordLikeRepository.save(recordLike);
    }

    @Transactional
    public void deleteRecordLike(User user, Record record) {
        RecordLike like = recordLikeRepository.findByLikedRecordAndLikedUser(record, user).orElseThrow(()->
                new TripRecordException(ErrorCode.RECORD_LIKE_NOT_FOUND));
        recordLikeRepository.delete(like);
    }

}
