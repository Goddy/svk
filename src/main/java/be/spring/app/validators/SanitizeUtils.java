package be.spring.app.validators;

import com.google.common.base.Strings;
import org.apache.commons.lang3.StringEscapeUtils;
import org.owasp.html.Sanitizers;

/**
 * Created by u0090265 on 12/31/14.
 */
public class SanitizeUtils {
    public static String SanitizeHtml(String text) {
        if (Strings.isNullOrEmpty(text)) return text;
        String r = Sanitizers.FORMATTING.and(Sanitizers.IMAGES).and(Sanitizers.LINKS).and(Sanitizers.BLOCKS).sanitize(text);
        return StringEscapeUtils.unescapeHtml4(r);
    }
}
