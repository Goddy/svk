package be.spring.app.utils;

import be.spring.app.model.Account;
import be.spring.app.model.Match;
import be.spring.app.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by u0090265 on 8/30/14.
 */
@Component
public class HtmlHelper {
    @Autowired
    private MessageSource messageSource;

    private static final String EDIT = "glyphicon glyphicon-edit edit";
    private static final String DELETE = "glyphicon glyphicon-trash delete";
    private static final String MAP = "glyphicon glyphicon-map-marker";
    private static final String DOODLE = "glyphicon glyphicon-th-list";
    private static final String MOTM = "glyphicon glyphicon-user";
    private static final String OK = "glyphicon glyphicon-ok green presence";
    private static final String REMOVE = "glyphicon glyphicon-remove red presence";
    private static final String DETAILS = "glyphicon glyphicon-eye-open";
    private static final String DELETE_CLASS = "delete";
    private static final String PRESENCE_CLASS = "presence";
    private static final String PRESENCE_BOX = "presenceBox";
    private static final String MAP_CLASS = "map";
    private static final String DETAILS_CLASS = "details";
    private static final String DOODLE_CLASS = "doodle";
    private static final String MOTM_CLASS = "motm";
    private static final String EMPTY = "";
    private static final String HTML_ACTIONS = "htmlActions";
    private static final String PRESENCE_ACTIONS = "presenceActions";

    public Map<String, String> getMatchesAdditions(Match match, Account account, Locale locale) {
        Map<String, String> r = new HashMap<>();
        if (account != null) {
            r.put(PRESENCE_ACTIONS, getPresenceBtns(match, account));
        }
        return r;
    }

    public String getPresenceBtns(Match match, Account account) {
        /**
        return match.getMatchDoodle().isPresent(account) ?
                getDoodleBtns(PRESENCE_CLASS, OK, String.format("/doodle/changeMatchDoodle.json?matchId=%s&present=false", match.getId())) :
                getDoodleBtns(PRESENCE_CLASS, REMOVE, String.format("/doodle/changeMatchDoodle.json?matchId=%s&present=true", match.getId()));
         **/
        return null;
    }

    private String getDoodleBtns(String aClazz, String clazz, String url) {
        StringBuilder stringBuilder = new StringBuilder().append(getBtn(aClazz, clazz, url));
        stringBuilder.append(clazz.equals(OK) ? getCheckBox(true, PRESENCE_BOX) : getCheckBox(false, PRESENCE_BOX));
        return stringBuilder.toString();

    }

    private String getCheckBox(boolean checked, String name) {
        return "<input type=\"checkbox\" style=\"display:none\"" + (checked ? "checked" : "") + "name=\"" + name + "\">";
    }

    public Map<String, String> getMatchesButtons(Match match, boolean isAdmin, Locale locale) {
        Map<String, String> m = new HashMap<>();
        StringBuilder btns = new StringBuilder();
        if (isAdmin) {
            btns.append(getBtn(EMPTY, EDIT, String.format("changeMatch.html?matchId=%s", match.getId()), messageSource.getMessage("title.changeMatchResult", null, locale)))
                    .append(getBtn(DELETE_CLASS, DELETE, String.format("deleteMatch.html?matchId=%s", match.getId()), messageSource.getMessage("title.deleteMatch", null, locale)));
        }

        String googleLink = match.getHomeTeam().getAddress().getGoogleLink();
        if (googleLink != null)
            btns.append(getBtn(MAP_CLASS, MAP, googleLink, messageSource.getMessage("title.matchLocation", null, locale)));

        if (!match.getGoals().isEmpty())
            btns.append(getBtn(DETAILS_CLASS, DETAILS, "details" + match.getId(), messageSource.getMessage("title.matchDetails", null, locale)));

        btns.append(getBtn(DOODLE_CLASS, DOODLE, String.format("getDoodle.html?matchId=%s", match.getId()), messageSource.getMessage("title.matchDoodle", null, locale)));
        btns.append(getBtn(MOTM_CLASS, MOTM, "#", messageSource.getMessage("title.manOfTheMatchPoll", null, locale)));

        m.put(HTML_ACTIONS, btns.toString().isEmpty() ? messageSource.getMessage("text.noActions", null, locale) : wrapIntoBtnGroup(btns.toString()));
        return m;
    }

    public Map getTeamButtons(Team team, boolean isAdmin, Locale locale) {
        Map<String, String> m = new HashMap<>();
        StringBuilder btns = new StringBuilder();
        if (team.getAddress().getGoogleLink() != null)
            btns.append(getBtn(MAP_CLASS, MAP, "#", messageSource.getMessage("title.teamLocation", null, locale)));

        if (isAdmin) {
            btns.append(getBtn(EMPTY, EDIT, String.format("editTeam.html?teamId=%s", team.getId()), messageSource.getMessage("title.editTeam", null, locale)))
                    .append(getBtn(DELETE_CLASS, DELETE, String.format("deleteTeam.html?teamId=%s", team.getId()), messageSource.getMessage("title.deleteTeam", null, locale)));
        }

        m.put(HTML_ACTIONS, btns.toString().isEmpty() ? messageSource.getMessage("text.noActions", null, locale) : wrapIntoBtnGroup(btns.toString()));
        return m;
    }

    private static String getBtn(String aClazz, String clazz, String url) {
        return String.format("<a href='%s' data-toggle='tooltip' data-placement='top' class='btn btn-default %s %s'/>", url, clazz, aClazz);
    }

    private static String getBtn(String aClazz, String clazz, String url, String title) {
        return String.format("<a href='%s' title='%s' data-toggle='tooltip' data-placement='top' class='btn btn-default %s %s'/>", url, title, clazz, aClazz);
    }

    private static String wrapIntoBtnGroup(String s) {
        return "<div class=\"btn-group\">" + s + "</div>";
    }
}
