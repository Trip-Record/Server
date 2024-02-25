package com.triprecord.triprecord.global.util;

public record ResponseMessage(
        String message
) {
    public String toWrite() {
        return "{" +
                "\"message\":" + "\"" + message +"\"" +
                "}";
    }
}
