package com.triprecord.triprecord.global.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저 정보를 찾을 수 없습니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    FILE_READ_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "파일 읽기에 실패했습니다."),

    IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "일치하는 기록 이미지가 없습니다."),

    PLACE_NOT_FOUND(HttpStatus.NOT_FOUND, "일치하는 장소가 없습니다."),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "권한이 존재하지 않습니다."),

    RECORD_ALREADY_LIKED(HttpStatus.CONFLICT, "이미 좋아요를 남긴 기록입니다."),
    RECORD_LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 기록에 좋아요를 남기지 않았습니다."),
    RECORD_COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."),
    RECORD_COMMENT_DUPLICATE(HttpStatus.CONFLICT, "기존 기록 댓글과 동일합니다."),
    INVALID_DATE(HttpStatus.BAD_REQUEST, "기간이 잘못되었습니다."),
    INVALID_RECORD_PLACE_SIZE(HttpStatus.BAD_REQUEST, "기록할 수 있는 최대 장소 수를 초과하였습니다."),
    INVALID_RECORD_IMAGE_SIZE(HttpStatus.BAD_REQUEST, "기록할 수 있는 최대 사진 수를 초과하였습니다."),

    RECORD_NOT_FOUND(HttpStatus.NOT_FOUND, "기록을 찾을 수 없습니다."),

    PLACE_DUPLICATE(HttpStatus.CONFLICT, "기존 장소와 동일합니다."),

    SCHEDULE_DATE_INVALID(HttpStatus.BAD_REQUEST, "일정 기간이 유효하지 않습니다."),
    SCHEDULE_DETAIL_DATE_INVALID(HttpStatus.BAD_REQUEST, "일정 상세 날짜가 일정 기간을 벗어났습니다."),
    SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "일치하는 일정이 없습니다."),
    SCHEDULE_LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 일정에 좋아요를 남기지 않았습니다."),
    SCHEDULE_ALREADY_LIKED(HttpStatus.CONFLICT, "이미 좋아요를 남긴 일정입니다."),
    SCHEDULE_COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "일치하는 일정 댓글이 없습니다."),
    SCHEDULE_COMMENT_DUPLICATE(HttpStatus.CONFLICT, "기존 일정 댓글과 동일합니다.");

    private final HttpStatus httpStatus;
    private final String message;
}