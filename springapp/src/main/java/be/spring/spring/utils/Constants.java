package be.spring.spring.utils;

/**
 * User: Tom De Dobbeleer
 * Date: 12/11/13
 * Time: 3:11 PM
 * Remarks: none
 */
public class Constants {
    public static final String PASSWORD_REGEX = "(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[^a-zA-Z]).{8,}";
    public static final int MINUS_TEN = -10;
    public static final int TEN = 10;
    public static final int ONE = 1;
    public static final int ZERO = 0;

    public static final String DEFAULT_ROLE = "USER";
    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";

    private Constants() {}
}
