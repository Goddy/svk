package be.spring.app.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by u0090265 on 5/17/14.
 */
public class ValidationHelper {
    private static final Pattern emailPattern = Pattern.compile(Constants.EMAIL_REGEX);
    private static final Pattern passwordPattern = Pattern.compile(Constants.PASSWORD_REGEX);

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
        return input != null && passwordPattern.matcher(input).matches();
    }

    public static boolean isEmailMatch(String input) {
        return input != null && emailPattern.matcher(input).matches();
    }

}
