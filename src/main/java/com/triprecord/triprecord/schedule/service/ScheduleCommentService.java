package com.triprecord.triprecord.schedule.service;

import com.triprecord.triprecord.global.exception.ErrorCode;
import com.triprecord.triprecord.global.exception.TripRecordException;
import com.triprecord.triprecord.schedule.dto.request.ScheduleCommentContentRequest;
import com.triprecord.triprecord.schedule.entity.Schedule;
import com.triprecord.triprecord.schedule.entity.ScheduleComment;
import com.triprecord.triprecord.schedule.repository.ScheduleCommentRepository;
import com.triprecord.triprecord.user.entity.User;
import com.triprecord.triprecord.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleCommentService {

    private final ScheduleCommentRepository scheduleCommentRepository;
    private final UserService userService;

    public Long getScheduleCommentCount(Schedule schedule) {
        return scheduleCommentRepository.countByCommentedSchedule(schedule);
    }

    @Transactional
    public void updateScheduleComment(Long userId, Long scheduleCommentId, ScheduleCommentContentRequest request) {
        User user = userService.getUserOrException(userId);
        ScheduleComment scheduleComment = getScheduleCommentOrException(scheduleCommentId);
        if (scheduleComment.getCommentedUser() != user) {
            throw new TripRecordException(ErrorCode.INVALID_PERMISSION);
        }
        String content = request.content();
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
