package be.spring.app.handler;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by u0090265 on 9/24/14.
 */

public class PropertiesAddingHandlerInterceptor extends HandlerInterceptorAdapter {

    private String baseUrl;

    public PropertiesAddingHandlerInterceptor(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public void postHandle(final HttpServletRequest request,
                           final HttpServletResponse response, final Object handler,
                           final ModelAndView modelAndView) throws Exception {

        if (modelAndView != null) {
            modelAndView.getModelMap().
                    addAttribute("baseUrl",
                            baseUrl);
        }
    }
}
