package be.spring.spring.controller;

import be.spring.spring.form.CreateTeamForm;
import be.spring.spring.interfaces.TeamService;
import be.spring.spring.model.Team;
import be.spring.spring.validators.CreateTeamValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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

    @Autowired
    private CreateTeamValidator validator;

    private static final Logger log = LoggerFactory.getLogger(TeamController.class);

    @InitBinder("form")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }


    @RequestMapping(value = "createTeam", method = RequestMethod.GET)
    public String getCreateMatch(ModelMap model, @ModelAttribute("form") CreateTeamForm form) {
        return LANDING_CREATE_TEAM;

    }

    @RequestMapping(value = "createTeam", method = RequestMethod.POST)
    public String createMatch(@ModelAttribute("form") @Valid CreateTeamForm form, BindingResult result, ModelMap model) {
        try {
            if (teamService.teamExists(form.getTeamName())) {
                result.rejectValue("teamName", "validation.teamExists.message");
            }

            if (result.hasErrors()) {
                return LANDING_CREATE_TEAM;
            }
            Team team = teamService.createTeam(form);
            log.debug("Created team: {}", team);
            return LANDING_TEAMS;
        } finally {
            log.info("Finally of createTeam");
        }


    }
}
