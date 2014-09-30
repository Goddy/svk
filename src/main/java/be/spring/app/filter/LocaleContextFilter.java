package be.spring.app.filter;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

/**
 * Created by u0090265 on 9/30/14.
 * Created to get localized security message
 * http://stackoverflow.com/questions/1373407/how-to-display-custom-error-message-in-jsp-for-spring-security-auth-exception
 */
public class LocaleContextFilter extends OncePerRequestFilter {
    private LocaleResolver localeResolver;
    public void setLocaleResolver(LocaleResolver localeResolver) {
        this.localeResolver = localeResolver;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // store Local into ThreadLocale
        if (this.localeResolver != null) {
            final Locale locale = this.localeResolver.resolveLocale(request);
            LocaleContextHolder.setLocale(locale);
        }
        try {
            filterChain.doFilter(request, response);
        } finally {
            LocaleContextHolder.resetLocaleContext();
        }
    }
}
