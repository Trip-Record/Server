package com.triprecord.triprecord.record.repository;

import com.triprecord.triprecord.record.entity.Record;
import com.triprecord.triprecord.record.entity.RecordLike;
import com.triprecord.triprecord.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordLikeRepository extends JpaRepository<RecordLike, Long> {

    Long countByLikedRecord(Record record);
    Long countRecordLikeByLikedUser(User user);
    Optional<RecordLike> findByLikedRecordAndLikedUser(Record record, User likedUser);
}
