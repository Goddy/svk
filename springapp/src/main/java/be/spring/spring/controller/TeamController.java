package be.spring.spring.controller;

import be.spring.spring.form.CreateTeamForm;
import be.spring.spring.interfaces.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

import static be.spring.spring.utils.Constants.LANDING_CREATE_TEAM;
import static be.spring.spring.utils.Constants.LANDING_TEAMS;

/**
 * Created by u0090265 on 5/11/14.
 */
@Controller
@RequestMapping("/teams")
public class TeamController {
    @Autowired
    TeamService teamService;

    @RequestMapping(value = "createTeam", method = RequestMethod.GET)
    public String getCreateMatch(ModelMap model, @ModelAttribute("form") CreateTeamForm form) {
        return LANDING_CREATE_TEAM;

    }

    @RequestMapping(value = "createTeam", method = RequestMethod.POST)
    public String createMatch(@ModelAttribute("form") @Valid CreateTeamForm form) {
        teamService.createTeam(form);
        return LANDING_TEAMS;
    }
}
