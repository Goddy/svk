package be.spring.app.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by u0090265 on 5/17/14.
 */
public class ValidationHelper {
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
        return input != null && input.matches(Constants.PASSWORD_REGEX);
    }
}
