package com.triprecord.triprecord.record.repository;


import com.triprecord.triprecord.record.entity.Record;
import com.triprecord.triprecord.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

    Long countRecordByCreatedUser(User user);

}
