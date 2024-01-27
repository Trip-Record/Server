package com.triprecord.triprecord.record.repository;

import com.triprecord.triprecord.record.entity.Record;
import com.triprecord.triprecord.record.entity.RecordComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RecordCommentRepository extends JpaRepository<RecordComment, Long> {

    Long countByCommentedRecord(Record record);
}
