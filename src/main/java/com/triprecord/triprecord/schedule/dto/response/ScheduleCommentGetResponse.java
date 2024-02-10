package com.triprecord.triprecord.schedule.dto.response;

import static com.triprecord.triprecord.global.util.Formatter.getDateTime;

import com.triprecord.triprecord.schedule.entity.ScheduleComment;
import com.triprecord.triprecord.user.dto.response.UserProfile;
import java.time.format.DateTimeFormatter;

public record ScheduleCommentGetResponse(
        UserProfile userProfile,
        String commentContent,
        String commentCreatedTime
) {
    public static ScheduleCommentGetResponse of(ScheduleComment scheduleComment) {
        return new ScheduleCommentGetResponse(
                UserProfile.of(scheduleComment.getCommentedUser()),
                scheduleComment.getScheduleCommentContent(),
                getDateTime(scheduleComment.getCreatedTime())
        );
    }
}
