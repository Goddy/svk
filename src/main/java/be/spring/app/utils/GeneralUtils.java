package be.spring.app.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;

/**
 * Created by u0090265 on 9/22/14.
 */
public class GeneralUtils {
    public static long convertToLong(String element) {
        return Long.parseLong(element);
    }
    public static String generateRandomUUID() {
        return UUID.randomUUID().toString();
    }
    public static String generateRandomHex(int length) {
        return RandomStringUtils.randomAlphanumeric(length).toUpperCase();
    }
}
