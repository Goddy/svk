package be.spring.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by u0090265 on 8/27/15.
 */
@Controller
public class FaqController {
    @RequestMapping(value = "/faq", method = RequestMethod.GET)
    public String home() {
        return "faq";
    }
}
