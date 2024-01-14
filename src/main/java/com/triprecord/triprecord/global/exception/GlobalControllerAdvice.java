package com.triprecord.triprecord.global.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    private final String ERROR_LOG = "[ERROR] %s %s";

    @ExceptionHandler(TripRecordException.class)
    public ResponseEntity<ExceptionDTO> applicationException(final TripRecordException e){
        log.error(String.format(ERROR_LOG, e.getHttpStatus(), e.getMessage()));
        return ResponseEntity.status(e.getHttpStatus()).body(new ExceptionDTO(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDTO> methodArgumentNotValidException(final MethodArgumentNotValidException e){
        log.error(String.format(ERROR_LOG, e.getParameter(), "객체검증에러"));
        return ResponseEntity.badRequest().body(new ExceptionDTO("필요한 데이터가 모두 입력되지 않았습니다."));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionDTO> unexpectedRuntimeException(final RuntimeException e){
        log.error(String.format(ERROR_LOG, e.getClass().getSimpleName(), e.getMessage()));
        return ResponseEntity.badRequest().body(new ExceptionDTO("unexpected runtime exception"));
    }

    record ExceptionDTO(String message){
    }
}
