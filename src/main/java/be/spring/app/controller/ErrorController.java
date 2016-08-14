package be.spring.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by u0090265 on 9/11/14.
 */
@Controller
public class ErrorController {
    @RequestMapping(value="/error-400", method = {RequestMethod.GET, RequestMethod.POST})
    public String error400(ModelMap map) {
        map.put("title", "error_400_title");
        return "error-400";
    }

    @RequestMapping(value="/error-403", method = {RequestMethod.GET, RequestMethod.POST})
    public String error403(ModelMap map) {
        map.put("title", "error_403_title");
        return "error-403";
    }

    @RequestMapping(value="/error-404", method = {RequestMethod.GET, RequestMethod.POST})
    public String error404(ModelMap map) {
        map.put("title", "error_404_title");
        return "error-404";
    }

    @RequestMapping(value="/error-500", method = {RequestMethod.GET, RequestMethod.POST})
    public String error500(ModelMap map) {
        map.put("title", "error_500_title");
        return "error-500";
    }

    @RequestMapping(value="/error-social-login-failed", method = {RequestMethod.GET, RequestMethod.POST})
    public String errorSocialLoginFailed(ModelMap map) {
        return "/account/socialLoginFailed";
    }
}
