package com.triprecord.triprecord.schedule.repository;

import com.triprecord.triprecord.schedule.entity.Schedule;
import com.triprecord.triprecord.schedule.entity.ScheduleComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleCommentRepository extends JpaRepository<ScheduleComment, Long> {

    Long countByCommentedSchedule(Schedule schedule);

    Page<ScheduleComment> findAllByCommentedSchedule(Schedule commentedSchedule, Pageable pageable);
}
