package com.triprecord.triprecord.schedule.repository;

import com.triprecord.triprecord.schedule.entity.Schedule;
import com.triprecord.triprecord.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("select count(s) from Schedule s where s.createdUser = :user")
    Long scheduleCount(@Param("user") User user);

    @Query("select count(sc) from ScheduleLike sc where sc.likedUser = :user")
    Long scheduleLikes(@Param("user") User user);
}
