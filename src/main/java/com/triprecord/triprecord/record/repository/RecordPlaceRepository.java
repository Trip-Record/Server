package com.triprecord.triprecord.record.repository;

import com.triprecord.triprecord.location.entity.Place;
import com.triprecord.triprecord.record.entity.Record;
import com.triprecord.triprecord.record.entity.RecordPlace;
import com.triprecord.triprecord.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordPlaceRepository extends JpaRepository<RecordPlace, Long> {

    long countByLinkedRecord(Record linkedRecord);
    void deleteByLinkedRecordAndRecordPlace(Record linkedRecord, Place recordPlace);

    @Query("SELECT r.recordPlace FROM RecordPlace r WHERE r.linkedRecord = :record")
    List<Place> findAllPlaceByLinkedRecord(Record record);

    @Query("select count(distinct rp.recordPlace) from RecordPlace rp where rp.linkedRecord.createdUser = :user")
    Long placeCount(@Param("user") User user);
}
