package be.spring.spring.controller;

import be.spring.spring.form.CreateTeamForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static be.spring.spring.utils.Constants.LANDING_CREATE_TEAM;

/**
 * Created by u0090265 on 5/11/14.
 */
@Controller
@RequestMapping("/teams")
public class TeamController {

    @RequestMapping(value = "createTeam", method = RequestMethod.GET)
    public String getCreateMatch(ModelMap model, @ModelAttribute("form") CreateTeamForm form) {
        return LANDING_CREATE_TEAM;

    }
}
