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

    PLACE_NOT_FOUND(HttpStatus.NOT_FOUND, "일치하는 장소가 없습니다."),
    PLACE_DUPLICATE(HttpStatus.CONFLICT, "기존 장소와 동일합니다."),
    SCHEDULE_DATE_INVALID(HttpStatus.BAD_REQUEST, "일정 기간이 유효하지 않습니다."),
    SCHEDULE_DETAIL_DATE_INVALID(HttpStatus.BAD_REQUEST, "일정 상세 날짜가 일정 기간을 벗어났습니다."),
    SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "일치하는 일정이 없습니다."),

    UNAUTHORIZED_ACCESS(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
