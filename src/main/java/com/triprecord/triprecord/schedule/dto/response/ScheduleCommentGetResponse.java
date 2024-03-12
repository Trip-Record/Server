package com.triprecord.triprecord.schedule.dto.response;

import static com.triprecord.triprecord.global.util.Formatter.getDateTime;

import com.triprecord.triprecord.schedule.entity.ScheduleComment;
import com.triprecord.triprecord.user.dto.response.UserProfile;

public record ScheduleCommentGetResponse(
        UserProfile userProfile,
        Long commentId,
        String commentContent,
        String commentCreatedTime,
        boolean isUserCreated
) {
    public static ScheduleCommentGetResponse of(ScheduleComment scheduleComment, boolean isUserCreated) {
        return new ScheduleCommentGetResponse(
                UserProfile.of(scheduleComment.getCommentedUser()),
                scheduleComment.getScheduleCommentId(),
                scheduleComment.getScheduleCommentContent(),
                getDateTime(scheduleComment.getCreatedTime()),
                isUserCreated
        );
    }
}
