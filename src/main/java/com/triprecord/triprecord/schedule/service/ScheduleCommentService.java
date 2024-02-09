package com.triprecord.triprecord.schedule.service;

import com.triprecord.triprecord.global.exception.ErrorCode;
import com.triprecord.triprecord.global.exception.TripRecordException;
import com.triprecord.triprecord.schedule.dto.request.ScheduleCommentContentRequest;
import com.triprecord.triprecord.schedule.dto.response.ScheduleCommentGetResponse;
import com.triprecord.triprecord.schedule.dto.response.ScheduleCommentPageGetResponse;
import com.triprecord.triprecord.schedule.entity.Schedule;
import com.triprecord.triprecord.schedule.entity.ScheduleComment;
import com.triprecord.triprecord.schedule.repository.ScheduleCommentRepository;
import com.triprecord.triprecord.user.entity.User;
import com.triprecord.triprecord.user.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public ScheduleCommentPageGetResponse getScheduleComments(Schedule schedule, Pageable pageable) {
        Page<ScheduleComment> scheduleComments = scheduleCommentRepository.findAllByCommentedSchedule(schedule,
                pageable);
        List<ScheduleCommentGetResponse> scheduleGetResponses = scheduleComments.stream()
                .map(ScheduleCommentGetResponse::of)
                .toList();

        return ScheduleCommentPageGetResponse.builder()
                .totalPages(scheduleComments.getTotalPages())
                .pageNumber(scheduleComments.getNumber())
                .scheduleComments(scheduleGetResponses)
                .build();
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

    @Transactional
    public void deleteScheduleComment(Long userId, Long scheduleCommentId) {
        User user = userService.getUserOrException(userId);
        ScheduleComment scheduleComment = getScheduleCommentOrException(scheduleCommentId);
        if (scheduleComment.getCommentedUser() != user) {
            throw new TripRecordException(ErrorCode.INVALID_PERMISSION);
        }
        scheduleCommentRepository.delete(scheduleComment);
    }

    public ScheduleComment getScheduleCommentOrException(Long scheduleCommentId) {
        return scheduleCommentRepository.findById(scheduleCommentId).orElseThrow(() ->
                new TripRecordException(ErrorCode.SCHEDULE_COMMENT_NOT_FOUND));
    }

}
