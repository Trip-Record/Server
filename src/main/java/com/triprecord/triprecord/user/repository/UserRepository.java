package com.triprecord.triprecord.user.repository;

import com.triprecord.triprecord.record.entity.Record;
import com.triprecord.triprecord.record.entity.RecordPlace;
import com.triprecord.triprecord.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserEmail(String userEmail);

    @Query("select count(rc) from RecordLike rc where rc.likedUser = :user")
    Long recordLikes(@Param("user") User user);

    @Query("select count(sc) from ScheduleLike sc where sc.likedUser = :user")
    Long scheduleLikes(@Param("user") User user);

    @Query("select count(distinct rp.recordPlace) from RecordPlace rp where rp.linkedRecord.createdUser = :user")
    Long placeCount(@Param("user") User user);

    @Query("select count(r) from Record r where r.createdUser = :user")
    Long recordCount(@Param("user") User user);

    @Query("select count(s) from Schedule s where s.createdUser = :user")
    Long scheduleCount(@Param("user") User user);
}
