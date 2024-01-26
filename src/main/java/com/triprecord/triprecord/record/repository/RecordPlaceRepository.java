package com.triprecord.triprecord.record.repository;

import com.triprecord.triprecord.record.entity.RecordPlace;
import com.triprecord.triprecord.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordPlaceRepository extends JpaRepository<RecordPlace, Long> {

    @Query("select count(distinct rp.recordPlace) from RecordPlace rp where rp.linkedRecord.createdUser = :user")
    Long placeCount(@Param("user") User user);
}
