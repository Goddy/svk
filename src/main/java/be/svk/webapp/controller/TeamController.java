package be.svk.webapp.controller;

import be.svk.webapp.controller.exceptions.ObjectNotFoundException;
import be.svk.webapp.data.PositionsEnum;
import be.svk.webapp.form.CreateAndUpdateTeamForm;
import be.svk.webapp.model.Account;
import be.svk.webapp.dto.ActionWrapperDTO;
import be.svk.webapp.model.Address;
import be.svk.webapp.model.Team;
import be.svk.webapp.service.AccountService;
import be.svk.webapp.service.AddressService;
import be.svk.webapp.service.TeamService;
import be.svk.webapp.validators.CreateTeamValidator;
import be.svk.webapp.utils.Constants;
import com.google.common.collect.Lists;
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

    @ModelAttribute("addresses")
    public List<Address> addressList() {
        return addressService.getAllAddresses();
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
            return Constants.REDIRECT_LANDING_TEAMS;
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
            return Constants.REDIRECT_LANDING_TEAMS;
        } finally {
            log.info("Finally of createTeam");
        }


    }

    private String editTeam(ModelMap model, Locale locale) {
        model.addAttribute("title", getMessage("title.editTeam", null, locale));
        model.addAttribute("action", EDIT_TEAM);
        return Constants.LANDING_CREATE_TEAM;
    }

    private String createTeam(ModelMap model, Locale locale) {
        model.addAttribute("title", getMessage("title.createTeam", null, locale));
        model.addAttribute("action", CREATE_TEAM);
        return Constants.LANDING_CREATE_TEAM;
    }

    @RequestMapping(value = "teams", method = RequestMethod.GET)
    public String getTeams(ModelMap model, Locale locale) {
        model.addAttribute("teams", teamService.getTeams(getAccountFromSecurity(), locale));
        return Constants.LANDING_TEAMS;

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
        return Constants.LANDING_TEAMS;

    }

    @RequestMapping(value = "team", method = RequestMethod.GET)
    public String getTeam(ModelMap model, Locale locale) {
        model.put("players", getSortedAccounts());
        return Constants.LANDING_TEAM;

    }

    private List<ActionWrapperDTO<Team>> getTeams(Locale locale) {
        return teamService.getTeams(getAccountFromSecurity(), locale);
    }

    private Map<String, List<Account>> getSortedAccounts() {
        Map<String, List<Account>> players = Maps.newLinkedHashMap();
        List<Account> goalKeepers = Lists.newArrayList();
        List<Account> defenders = Lists.newArrayList();
        List<Account> midfielders = Lists.newArrayList();
        List<Account> fordwards = Lists.newArrayList();
        List<Account> unknown = Lists.newArrayList();

        for (Account account : accountService.getAllActivateAccounts()) {
            if (account.getAccountProfile() == null || account.getAccountProfile().getFavouritePosition() == null) {
                //No account profile, no position
                unknown.add(account);
            }
            else {
                switch (account.getAccountProfile().getFavouritePosition()) {
                    case GOALKEEPER:
                        goalKeepers.add(account);
                        break;
                    case DEFENDER:
                        defenders.add(account);
                        break;
                    case MIDFIELDER:
                        midfielders.add(account);
                        break;
                    case FORWARD:
                        fordwards.add(account);
                        break;
                    default:
                        unknown.add(account);
                        break;
                }
            }

        }
        players.put(PositionsEnum.GOALKEEPER.name(), goalKeepers);
        players.put(PositionsEnum.DEFENDER.name(), defenders);
        players.put(PositionsEnum.MIDFIELDER.name(), midfielders);
        players.put(PositionsEnum.FORWARD.name(), fordwards);
        players.put("UNKNOWN", unknown);
        return players;
    }
}
