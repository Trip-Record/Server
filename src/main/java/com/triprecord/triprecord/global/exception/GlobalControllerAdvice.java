package com.triprecord.triprecord.global.exception;


import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
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
        return ResponseEntity.badRequest().body(new ExceptionDTO("전달된 데이터에 오류가 있습니다."));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionDTO> unexpectedRuntimeException(final RuntimeException e){
        log.error(String.format(ERROR_LOG, e.getClass().getSimpleName(), e.getMessage()));
        return ResponseEntity.badRequest().body(new ExceptionDTO("예기치 않은 런타임 에러입니다."));
    }

    @ExceptionHandler(AmazonClientException.class)
    public ResponseEntity<ExceptionDTO> amazonClientException(final AmazonClientException e){
        log.error(String.format(ERROR_LOG, e.getClass().getSimpleName(), e.getMessage()));
        return ResponseEntity.internalServerError().body(new ExceptionDTO("아마존 클라이언트 에러입니다."));
    }

    @ExceptionHandler(AmazonServiceException.class)
    public ResponseEntity<ExceptionDTO> amazonServiceException(final AmazonServiceException e){
        log.error(String.format(ERROR_LOG, e.getClass().getSimpleName(), e.getMessage()));
        return ResponseEntity.internalServerError().body(new ExceptionDTO("아마존 서비스 에러입니다."));
    }

    record ExceptionDTO(String message){
    }
}
