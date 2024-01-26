package com.triprecord.triprecord.record.repository;

import com.triprecord.triprecord.record.entity.Record;
import com.triprecord.triprecord.record.entity.RecordImage;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordImageRepository extends JpaRepository<RecordImage, Long> {

    List<RecordImage> findAllByLinkedRecord(Record linkedRecord);
}
