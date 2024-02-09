package com.triprecord.triprecord.record.controller.request;

import jakarta.validation.constraints.NotBlank;

public record RecordCommentContent(
        @NotBlank String commentContent
) {
}
