package be.spring.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by u0090265 on 13/09/16.
 */
@Controller
public class CertController {
    @RequestMapping(value = "/.well-known/acme-challenge/2YLmM551GGVBdgGAEVjbayy2V90s2TDcPG3GtLDqgcM", method =
            RequestMethod.GET)
    @ResponseBody
    public String getCert() {
        return "2YLmM551GGVBdgGAEVjbayy2V90s2TDcPG3GtLDqgcM.nSuK_iD360mtpO2ZJoMCZU4m0BdFKZZ4o9_oJdhrBMA";
    }
}
