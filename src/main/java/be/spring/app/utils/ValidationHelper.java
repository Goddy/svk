package be.spring.app.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by u0090265 on 5/17/14.
 */
public class ValidationHelper {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(Constants.EMAIL_REGEX);
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(Constants.PASSWORD_REGEX);
    private static final Pattern PHONE_PATTERN = Pattern.compile(Constants.PASSWORD_REGEX);
    private static final Pattern NAME_PATTERN = Pattern.compile(Constants.NAME_REGEX);

    public static boolean isValidDate(String date) {
        try {
            returnDate(date);
            return true;
        } catch (ParseException e) {
            return false;
        }

    }

    public static Date returnDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        return sdf.parse(date);
    }

    public static boolean isPasswordMatch(String input) {
        return input != null && PASSWORD_PATTERN.matcher(input).matches();
    }

    public static boolean isPhoneMatch(String input) {
        return input != null && PHONE_PATTERN.matcher(input).matches();
    }

    public static boolean isNameMatch(String input) {
        return input != null && NAME_PATTERN.matcher(input).matches();
    }

    public static boolean isLength(String input, int length) {
        return input == null ? false : input.length() >= length;
    }

    public static boolean isEmailMatch(String input) {
        return input != null && EMAIL_PATTERN.matcher(input).matches();
    }

}
