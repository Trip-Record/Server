package com.triprecord.triprecord.record.dto;

import static com.triprecord.triprecord.global.util.Formatter.getDateTime;

import com.triprecord.triprecord.record.entity.RecordComment;
import com.triprecord.triprecord.user.dto.response.UserProfile;

public record RecordCommentData(
        UserProfile userProfile,
        String commentContent,
        String commentCreatedTime,
        boolean isUserCreated
) {

    public static RecordCommentData fromEntity(RecordComment recordComment, boolean isUserCreated) {
        return new RecordCommentData(
                UserProfile.of(recordComment.getCommentedUser()),
                recordComment.getCommentContent(),
                getDateTime(recordComment.getCreatedTime()),
                isUserCreated
        );
    }
}
