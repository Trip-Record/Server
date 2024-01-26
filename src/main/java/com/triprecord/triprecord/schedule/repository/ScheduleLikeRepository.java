package com.triprecord.triprecord.schedule.repository;

import com.triprecord.triprecord.schedule.entity.Schedule;
import com.triprecord.triprecord.schedule.entity.ScheduleLike;
import com.triprecord.triprecord.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleLikeRepository extends JpaRepository<ScheduleLike, Long> {

    Long countByLikedSchedule(Schedule schedule);
    Long countScheduleLikeByLikedUser(User user);
}
