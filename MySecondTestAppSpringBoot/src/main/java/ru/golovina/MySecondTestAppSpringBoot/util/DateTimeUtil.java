package ru.golovina.MySecondTestAppSpringBoot.util;

import java.text.SimpleDateFormat;
public class DateTimeUtil {
    public static SimpleDateFormat getCustomFormat() {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SsS'Z'");
    }
}
