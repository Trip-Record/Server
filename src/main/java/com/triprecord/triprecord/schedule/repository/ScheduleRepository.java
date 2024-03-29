package com.triprecord.triprecord.schedule.repository;

import com.triprecord.triprecord.schedule.entity.Schedule;
import com.triprecord.triprecord.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Long countScheduleByCreatedUser(User user);

    @Query("SELECT s FROM Schedule s ORDER BY s.scheduleId DESC")
    Page<Schedule> findAllOrderById(Pageable pageable);

    @Query("SELECT s FROM Schedule s WHERE s.createdUser.userId = :uid ORDER BY s.scheduleId DESC ")
    Page<Schedule> findAllByCreatedUser(@Param("uid") Long userId, Pageable pageable);
}
