package be.spring.spring.utils;

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
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        sdf.setLenient(false);
        return sdf.parse(date);
    }
}
