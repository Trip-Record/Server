package com.triprecord.triprecord.global.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"유저 정보를 찾을 수 없습니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED,"비밀번호가 일치하지 않습니다."),
    FILE_READ_FAILED(HttpStatus.INTERNAL_SERVER_ERROR,"파일 읽기에 실패했습니다."),

    PLACE_NOT_FOUNT(HttpStatus.NOT_FOUND,"일치하는 장소가 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
