package com.triprecord.triprecord.record.repository;


import com.triprecord.triprecord.record.entity.Record;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    Optional<Record> findByRecordId(Long recordId);
}
