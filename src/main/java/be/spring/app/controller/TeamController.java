package be.spring.app.controller;

import be.spring.app.controller.exceptions.ObjectNotFoundException;
import be.spring.app.data.PositionsEnum;
import be.spring.app.form.CreateAndUpdateTeamForm;
import be.spring.app.model.Account;
import be.spring.app.model.ActionWrapper;
import be.spring.app.model.Team;
import be.spring.app.service.AccountService;
import be.spring.app.service.AddressService;
import be.spring.app.service.TeamService;
import be.spring.app.validators.CreateTeamValidator;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static be.spring.app.utils.Constants.*;

/**
 * Created by u0090265 on 5/11/14.
 */
@Controller
@RequestMapping("/")
public class TeamController extends AbstractController {
    @Autowired
    TeamService teamService;

    @Autowired
    AddressService addressService;

    @Autowired
    AccountService accountService;

    @Autowired
    private CreateTeamValidator validator;

    private static final Logger log = LoggerFactory.getLogger(TeamController.class);
    private static final String EDIT_TEAM = "editTeam";
    private static final String CREATE_TEAM = "createTeam";

    @InitBinder("form")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "createTeam", method = RequestMethod.GET)
    public String getCreateTeamPage(ModelMap model, @ModelAttribute("form") CreateAndUpdateTeamForm form, Locale locale) {
        model.addAttribute("form", new CreateAndUpdateTeamForm());
        return createTeam(model, locale);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "editTeam", method = RequestMethod.GET)
    public String getEditTeamPage(@ModelAttribute("form") CreateAndUpdateTeamForm form, @RequestParam long teamId, ModelMap model, Locale locale) {
        Team team = teamService.getTeam(teamId);
        if (team == null) throw new ObjectNotFoundException(String.format("Team with id %s not found", teamId));
        form.setId(team.getId());
        form.setTeamName(team.getName());
        form.setCity(team.getAddress().getCity());
        form.setAddress(team.getAddress().getAddress());
        form.setPostalCode(String.valueOf(team.getAddress().getPostalCode()));
        if (team.getAddress().getGoogleLink() != null && !team.getAddress().getGoogleLink().isEmpty()) {
            form.setGoogleLink(team.getAddress().getGoogleLink());
            form.setUseLink(true);
        }
        model.addAttribute("selectedAddress", team.getAddress().getId());

        return editTeam(model, locale);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "createTeam", method = RequestMethod.POST)
    public String postCreateTeam(@ModelAttribute("form") @Valid CreateAndUpdateTeamForm form, BindingResult result, ModelMap model, Locale locale) {
        try {
            if (teamService.teamExists(form.getTeamName())) {
                result.rejectValue("teamName", "validation.teamExists.message");
            }

            if (result.hasErrors()) {
                return createTeam(model, locale);
            }
            Team team = teamService.createTeam(form);
            log.debug("Created team: {}", team);
            return REDIRECT_LANDING_TEAMS;
        } finally {
            log.info("Finally of createTeam");
        }


    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "editTeam", method = RequestMethod.POST)
    public String postEditTeam(@ModelAttribute("form") @Valid CreateAndUpdateTeamForm form, BindingResult result, ModelMap model, Locale locale) {
        try {
            if (result.hasErrors()) {
                model.addAttribute("form", form);
                return editTeam(model, locale);
            }
            Team team = teamService.updateTeam(form);
            log.debug("Created team: {}", team);
            return REDIRECT_LANDING_TEAMS;
        } finally {
            log.info("Finally of createTeam");
        }


    }

    private String editTeam(ModelMap model, Locale locale) {
        model.addAttribute("title", getMessage("title.editTeam", null, locale));
        model.addAttribute("action", EDIT_TEAM);
        return LANDING_CREATE_TEAM;
    }

    private String createTeam(ModelMap model, Locale locale) {
        model.addAttribute("title", getMessage("title.createTeam", null, locale));
        model.addAttribute("action", CREATE_TEAM);
        return LANDING_CREATE_TEAM;
    }

    @RequestMapping(value = "teams", method = RequestMethod.GET)
    public String getTeams(ModelMap model, Locale locale) {
        model.addAttribute("teams", teamService.getTeams(getAccountFromSecurity(), locale));
        return LANDING_TEAMS;

    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "deleteTeam", method = RequestMethod.GET)
    public String deleteTeam(@RequestParam long teamId, Model model, Locale locale) {
        Team team = teamService.getTeam(teamId);
        if (team == null) throw new ObjectNotFoundException(String.format("Team with id %s not found", teamId));
        if (teamService.deleteTeam(teamId, getAccountFromSecurity())) {
            setSuccessMessage(model, locale, "text.team.deleted", null);
        } else {
            setErrorMessage(model, locale, "error.delete.team.dependencies", null);
        }
        model.addAttribute("teams", getTeams(locale));
        return LANDING_TEAMS;

    }

    @RequestMapping(value = "team", method = RequestMethod.GET)
    public String getTeam(ModelMap model, Locale locale) {
        Map<String, List<Account>> players = Maps.newLinkedHashMap();
        players.put(PositionsEnum.GOALKEEPER.name(), accountService.getAllActivateAccounts());
        model.put("players", players);
        return LANDING_TEAM;

    }

    private List<ActionWrapper<Team>> getTeams(Locale locale) {
        return teamService.getTeams(getAccountFromSecurity(), locale);
    }
}
