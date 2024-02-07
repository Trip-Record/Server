package com.triprecord.triprecord.schedule.service;

import com.triprecord.triprecord.global.exception.ErrorCode;
import com.triprecord.triprecord.global.exception.TripRecordException;
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

    public void updateScheduleComment(User user, Long scheduleCommentId, String content) {
        ScheduleComment scheduleComment = getScheduleCommentOrException(scheduleCommentId);
        if (scheduleComment.getCommentedUser() != user) {
            throw new TripRecordException(ErrorCode.INVALID_PERMISSION);
        }
        if (scheduleComment.getScheduleCommentContent().equals(content)) {
            throw new TripRecordException(ErrorCode.SCHEDULE_COMMENT_DUPLICATE);
        }
        scheduleComment.updateContent(content);
    }

    public ScheduleComment getScheduleCommentOrException(Long scheduleCommentId) {
        return scheduleCommentRepository.findById(scheduleCommentId).orElseThrow(() ->
                new TripRecordException(ErrorCode.SCHEDULE_COMMENT_NOT_FOUND));
    }

}
