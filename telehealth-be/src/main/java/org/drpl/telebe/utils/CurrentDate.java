package org.drpl.telebe.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class CurrentDate {
    public static Date get() {
        return Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
    }
}
