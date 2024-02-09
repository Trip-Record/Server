package com.triprecord.triprecord.record.controller.request;

import jakarta.validation.constraints.NotBlank;

public record CommentContent(
        @NotBlank String commentContent
) {
}
