package com.triprecord.triprecord.schedule.service;

import com.triprecord.triprecord.schedule.entity.Schedule;
import com.triprecord.triprecord.schedule.entity.ScheduleComment;
import com.triprecord.triprecord.schedule.repository.ScheduleCommentRepository;
import com.triprecord.triprecord.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleCommentService {

    private final ScheduleCommentRepository scheduleCommentRepository;

    public Long getScheduleCommentCount(Schedule schedule) {
        return scheduleCommentRepository.countByCommentedSchedule(schedule);
    }

    @Transactional
    public void createScheduleComment(User user, Schedule schedule, String content) {
        ScheduleComment scheduleComment = ScheduleComment.builder()
                .user(user)
                .schedule(schedule)
                .content(content)
                .build();
        scheduleCommentRepository.save(scheduleComment);
    }
}
