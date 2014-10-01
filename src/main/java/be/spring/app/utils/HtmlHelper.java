package be.spring.app.utils;

import be.spring.app.model.*;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Collection;
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

    private static String EDIT = "glyphicon glyphicon-edit edit";
    private static String DELETE = "glyphicon glyphicon-trash delete";
    private static String MAP = "glyphicon glyphicon-map-marker";
    private static String OK = "glyphicon glyphicon-ok green presence";
    private static String REMOVE = "glyphicon glyphicon-remove red presence";
    private static String DELETE_CLASS = "delete";
    private static String PRESENCE_CLASS = "presence";
    private static String PRESENCE_BOX = "presenceBox";
    private static String MAP_CLASS = "map";
    private static String EMPTY = "";
    private static String HTML_ACTIONS = "htmlActions";
    private static String PRESENCE_ACTIONS = "presenceActions";

    public Map<String, String> getMatchesAdditions(Match match, Account account, Locale locale) {
        Map<String, String> r = new HashMap<>();
        if (account != null) {
            r.put(PRESENCE_ACTIONS, isPresent(match) ?
                    getDoodleBtns(PRESENCE_CLASS, OK, String.format("changeMatchPresence.html?matchId=%s", match.getId())) :
                    getDoodleBtns(PRESENCE_CLASS, REMOVE, String.format("changeMatchPresence.html?matchId=%s", match.getId())));
        }
        return r;
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
            btns.append(getBtn(EMPTY, EDIT, String.format("changeMatchResult.html?matchId=%s", match.getId())))
                    .append(getBtn(DELETE_CLASS, DELETE, String.format("deleteMatch.html?matchId=%s", match.getId())));
        }

        String googleLink = match.getHomeTeam().getAddress().getGoogleLink();
        if (googleLink != null) btns.append(getBtn(MAP_CLASS, MAP, googleLink));

        m.put(HTML_ACTIONS, btns.toString().isEmpty() ? messageSource.getMessage("text.noActions", null, locale) : wrapIntoBtnGroup(btns.toString()));
        return m;
    }

    public Map getTeamButtons(Team team, boolean isAdmin, Locale locale) {
        Map<String, String> m = new HashMap<>();
        StringBuilder btns = new StringBuilder();
        if (team.getAddress().getGoogleLink() != null) btns.append(getBtn(MAP_CLASS, MAP, "#"));

        if (isAdmin) {
            btns.append(getBtn(EMPTY, EDIT, String.format("editTeam.html?teamId=%s", team.getId())))
                .append(getBtn(DELETE_CLASS, DELETE, String.format("deleteTeam.html?teamId=%s", team.getId())));
        }

        m.put(HTML_ACTIONS, btns.toString().isEmpty() ? messageSource.getMessage("text.noActions", null, locale) : wrapIntoBtnGroup(btns.toString()));
        return m;
    }

    private static String getBtn(String aClazz, String clazz, String url) {
        return String.format("<a href='%s' data-toggle='tooltip' data-placement='top' class='btn btn-default %s'><span class='%s'></span></a>", url, clazz, aClazz);
    }

    private static String wrapIntoBtnGroup(String s) {
        return "<div class=\"btn-group\">" + s + "</div>";
    }

    private static boolean isPresent(Match m) {
        Doodle doodle = m.getMatchDoodle();
        if (doodle != null) {
            Collection<Presence> p = Collections2.filter(m.getMatchDoodle().getPresences(), new Predicate<Presence>() {
                @Override
                public boolean apply(Presence presence) {
                    return false;
                }
            });

            Presence presence = (Presence)Iterables.getFirst(p, Presence.class);
            return presence != null && presence.isPresent();
        }
        return false;
    }
}
