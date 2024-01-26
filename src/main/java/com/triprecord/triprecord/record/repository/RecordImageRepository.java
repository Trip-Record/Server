package com.triprecord.triprecord.record.repository;

import com.triprecord.triprecord.record.entity.Record;
import com.triprecord.triprecord.record.entity.RecordImage;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordImageRepository extends JpaRepository<RecordImage, Long> {

<<<<<<< HEAD
    long countByLinkedRecord(Record linkedRecord);
    Optional<RecordImage> findAllByLinkedRecordAndRecordImgUrl(Record linkedRecord, String recordImgUrl);
=======
    List<RecordImage> findAllByLinkedRecord(Record linkedRecord);
>>>>>>> 227406485355b02e4863dddd15c62f5935b5b7ff
}
