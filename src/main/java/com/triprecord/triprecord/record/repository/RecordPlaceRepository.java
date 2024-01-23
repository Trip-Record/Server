package com.triprecord.triprecord.record.repository;

import com.triprecord.triprecord.record.entity.RecordPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordPlaceRepository extends JpaRepository<RecordPlace, Long> {
}
