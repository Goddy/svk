package be.spring.spring.controller;

import be.spring.spring.controller.exceptions.ObjectNotFoundException;
import be.spring.spring.form.CreateTeamForm;
import be.spring.spring.interfaces.TeamService;
import be.spring.spring.model.ActionWrapper;
import be.spring.spring.model.Match;
import be.spring.spring.model.Team;
import be.spring.spring.validators.CreateTeamValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.Locale;

import static be.spring.spring.utils.Constants.LANDING_CREATE_TEAM;
import static be.spring.spring.utils.Constants.LANDING_TEAMS;

/**
 * Created by u0090265 on 5/11/14.
 */
@Controller
@RequestMapping("/teams")
public class TeamController extends AbstractController {
    @Autowired
    TeamService teamService;

    @Autowired
    private CreateTeamValidator validator;

    private static final Logger log = LoggerFactory.getLogger(TeamController.class);

    @InitBinder("form")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "createTeam", method = RequestMethod.GET)
    public String getCreateTeamPage(ModelMap model, @ModelAttribute("form") CreateTeamForm form) {
        model.addAttribute("form", new CreateTeamForm());
        return LANDING_CREATE_TEAM;

    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "editTeam", method = RequestMethod.GET)
    public String getEditTeamPage(@ModelAttribute("form") CreateTeamForm form, @RequestParam String teamId) {
        Team team = teamService.getTeam(teamId);
        if (team == null) throw new ObjectNotFoundException(String.format("Team with id %s not found", teamId));
        form.setTeamName(team.getName());
        form.setCity(team.getAddress().getCity());
        form.setAddress(team.getAddress().getAddress());
        form.setPostalCode(String.valueOf(team.getAddress().getPostalCode()));
        if (team.getAddress().getGoogleLink() != null && !team.getAddress().getGoogleLink().isEmpty()) {
            form.setGoogleLink(team.getAddress().getGoogleLink());
            form.setUseLink(true);
        }
        return LANDING_CREATE_TEAM;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "createTeam", method = RequestMethod.POST)
    public String createMatch(@ModelAttribute("form") @Valid CreateTeamForm form, BindingResult result, ModelMap model) {
        try {
            if (teamService.teamExists(form.getTeamName())) {
                result.rejectValue("teamName", "validation.teamExists.message");
            }

            if (result.hasErrors()) {
                model.addAttribute("form", form);
                return LANDING_CREATE_TEAM;
            }
            Team team = teamService.createTeam(form);
            log.debug("Created team: {}", team);
            return "redirect:" + LANDING_TEAMS + ".html";
        } finally {
            log.info("Finally of createTeam");
        }


    }

    @RequestMapping(value = "teams", method = RequestMethod.GET)
    public String getTeams(ModelMap model, Locale locale) {
        model.addAttribute("teams", teamService.getTeams(getAccountFromSecurity(), locale));
        return LANDING_TEAMS;

    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "deleteTeam", method = RequestMethod.GET)
    public String deleteTeam(@RequestParam String teamId, ModelMap model, Locale locale) {
        Team team = teamService.getTeam(teamId);
        if (team == null) throw new ObjectNotFoundException(String.format("Team with id %s not found", teamId));
        if (teamService.deleteTeam(teamId, getAccountFromSecurity())) {
            setSuccessMessage(model, locale, "text.team.deleted", null);
        }
        else {
            setErrorMessage(model, locale, "error.delete.team.dependencies", null);
        }
        model.addAttribute("teams", getTeams(locale));
        return LANDING_TEAMS;

    }

    private List<ActionWrapper<Team>> getTeams(Locale locale) {
        return teamService.getTeams(getAccountFromSecurity(), locale);
    }
}
