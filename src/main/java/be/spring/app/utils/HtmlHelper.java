package be.spring.app.utils;

import be.spring.app.model.Match;
import be.spring.app.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

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
    private static String DELETE_CLASS = "delete";
    private static String MAP_CLASS = "map";
    private static String EMPTY = "";

    public String getMatchesButtons(Match match, boolean isAdmin, Locale locale) {
        StringBuilder btns = new StringBuilder();
        if (isAdmin) {
            btns.append(getBtn(EMPTY, EDIT, String.format("changeMatchResult.html?matchId=%s", match.getId())))
                    .append(getBtn(DELETE_CLASS, DELETE, String.format("deleteMatch.html?matchId=%s", match.getId())));
        }

        String googleLink = match.getHomeTeam().getAddress().getGoogleLink();
        if (googleLink != null) btns.append(getBtn(MAP_CLASS, MAP, googleLink));

        return btns.toString().isEmpty() ? messageSource.getMessage("text.noActions", null, locale) : wrapIntoBtnGroup(btns.toString());
    }

    public String getTeamButtons(Team team, boolean isAdmin, Locale locale) {
        StringBuilder btns = new StringBuilder();
        if (team.getAddress().getGoogleLink() != null) btns.append(getBtn(MAP_CLASS, MAP, "#"));

        if (isAdmin) {
            btns.append(getBtn(EMPTY, EDIT, String.format("editTeam.html?teamId=%s", team.getId())))
                .append(getBtn(DELETE_CLASS, DELETE, String.format("deleteTeam.html?teamId=%s", team.getId())));
        }

        return btns.toString().isEmpty() ? messageSource.getMessage("text.noActions", null, locale) : wrapIntoBtnGroup(btns.toString());
    }

    private static String getBtn(String aClazz, String clazz, String url) {
        return String.format("<a href='%s' data-toggle='tooltip' data-placement='top' class='btn btn-default %s'><span class='%s'></span></a>", url, clazz, aClazz);
    }

    private static String wrapIntoBtnGroup(String s) {
        return "<div class=\"btn-group\">" + s + "</div>";
    }
}
