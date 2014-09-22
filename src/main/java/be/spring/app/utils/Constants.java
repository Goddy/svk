package be.spring.app.utils;

/**
 * User: Tom De Dobbeleer
 * Date: 12/11/13
 * Time: 3:11 PM
 * Remarks: none
 */
public class Constants {
    public static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,15}$";
    public static final int MINUS_TEN = -10;
    public static final int TEN = 10;
    public static final int ONE = 1;
    public static final int ZERO = 0;

    public static final String DEFAULT_ROLE = "USER";
    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";

    public static final String LANDING_CREATE_TEAM = "/teams/createTeam";
    public static final String LANDING_TEAMS = "/teams/teams";

    private Constants() {}
}
