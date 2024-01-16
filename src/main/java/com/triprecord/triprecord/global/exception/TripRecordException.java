package com.triprecord.triprecord.global.exception;



import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class TripRecordException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String getMessage(){
        return message;
    }

    public TripRecordException(ErrorCode errorCode){
        this.httpStatus = errorCode.getHttpStatus();
        this.message = errorCode.getMessage();
    }

}
