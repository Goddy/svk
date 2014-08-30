package be.spring.spring.utils;

import be.spring.spring.model.Match;
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

    private static String EDIT = "glyphicon glyphicon-edit";
    private static String DELETE = "glyphicon glyphicon-trash";

    public String getMatchesButtons(Match match, boolean isAdmin, Locale locale) {
        if (isAdmin) {
            String btns = new StringBuilder()
                    .append(getBtn(EDIT, String.format("changeMatchResult.html?id=%s", match.getId())))
                    .append(getBtn(DELETE, String.format("deleteMatch.html?id=%s", match.getId())))
                    .toString();
            return wrapIntoBtnGroup(btns);
        }
        else {
            return messageSource.getMessage("text.noActions", null, locale);
        }
    }

    private static String getBtn(String clazz, String url) {
        return String.format("<a href='%s' data-toggle='tooltip' data-placement='top' class='btn btn-default'><span class='%s'></span></a>", url, clazz);
    }

    private static String wrapIntoBtnGroup(String s) {
        return "<div class=\"btn-group\">" + s + "</div>";
    }
}
