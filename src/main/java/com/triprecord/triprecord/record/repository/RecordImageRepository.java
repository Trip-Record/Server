package com.triprecord.triprecord.record.repository;

import com.triprecord.triprecord.record.entity.Record;
import com.triprecord.triprecord.record.entity.RecordImage;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordImageRepository extends JpaRepository<RecordImage, Long> {

    long countByLinkedRecord(Record linkedRecord);
    Optional<RecordImage> findByLinkedRecordAndRecordImgUrl(Record linkedRecord, String recordImgUrl);
    List<RecordImage> findAllByLinkedRecord(Record linkedRecord);
}
