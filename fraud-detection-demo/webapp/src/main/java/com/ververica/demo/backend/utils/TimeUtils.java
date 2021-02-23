package com.ververica.demo.backend.utils;

import java.time.Instant;

public class TimeUtils {
    public static Long convertStringToUnixTimestamp(String stringTimestamp){
        return Instant.parse(stringTimestamp).toEpochMilli();
    }
}
