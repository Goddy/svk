package be.spring.app.utils;

import be.spring.app.controller.exceptions.ObjectNotFoundException;
import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.UUID;

/**
 * Created by u0090265 on 9/22/14.
 */
public class GeneralUtils {
    private static final DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("dd/MM/YYYY");
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd/MM/YYYY - HH:mm");
    private static final DateTimeFormatter hourFormatter = DateTimeFormat.forPattern("HH:mm");

    private static final DateTimeZone defaultTimeZone = DateTimeZone.forID("Europe/Brussels");

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
        if (dateTime == null) return null;
        return dateFormatter.print(dateTime);
    }

    public static String convertToStringDateTimeWithTimeZone(DateTime dateTime) {
        if (dateTime == null) return null;
        return dateTimeFormatter.withZone(defaultTimeZone).print(dateTime);
    }

    public static String convertToStringDate(Date date) {
        if (date == null) return null;
        return dateFormatter.print(new DateTime(date));
    }

    public static DateTime convertToDate(String date) {
        return dateFormatter.parseDateTime(date);
    }

    public static String convertToStringHour(DateTime dateTime) {
        return hourFormatter.print(dateTime);
    }

    public static String trim(String input) {
        return input == null ? "" : input.trim();
    }

    public static void throwObjectNotFoundException(Object o, Long id, Class expectedClass) {
        if (o == null) throw new ObjectNotFoundException(String.format("%s %s not found", expectedClass.getName(), id));
    }
}
