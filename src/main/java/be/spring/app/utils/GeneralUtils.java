package be.spring.app.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.UUID;

/**
 * Created by u0090265 on 9/22/14.
 */
public class GeneralUtils {
    private static final DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("dd/MM/YYYY");
    private static final DateTimeFormatter hourFormatter = DateTimeFormat.forPattern("HH:mm");

    public static long convertToLong(String element) {
        return Long.parseLong(element);
    }
    public static String generateRandomUUID() {
        return UUID.randomUUID().toString();
    }
    public static String generateRandomHex(int length) {
        return RandomStringUtils.randomAlphanumeric(length).toUpperCase();
    }

    public static String convertToStringDate(DateTime dateTime) {
        return dateFormatter.print(dateTime);
    }

    public static String convertToStringHour(DateTime dateTime) {
        return hourFormatter.print(dateTime);
    }

    public static String trim(String input) {
        return input == null ? "" : input.trim();
    }
}
