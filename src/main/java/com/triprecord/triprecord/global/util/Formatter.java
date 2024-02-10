package com.triprecord.triprecord.global.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Formatter {

    public static String getDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        return dateTime.format(formatter);
    }
}
