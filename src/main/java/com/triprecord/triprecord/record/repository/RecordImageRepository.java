package com.triprecord.triprecord.record.repository;

import com.triprecord.triprecord.record.entity.RecordImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordImageRepository extends JpaRepository<RecordImage, Long> {
}
