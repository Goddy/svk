package be.spring.app.controller;

import be.spring.app.controller.exceptions.ObjectNotFoundException;
import be.spring.app.form.CreateMatchForm;
import be.spring.app.model.Account;
import be.spring.app.model.ActionWrapper;
import be.spring.app.model.Match;
import be.spring.app.service.AccountService;
import be.spring.app.service.MatchesService;
import be.spring.app.service.SeasonService;
import be.spring.app.service.TeamService;
import be.spring.app.validators.CreateMatchValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

/**
 * Created by u0090265 on 5/3/14.
 */
@Controller
@RequestMapping("/matches")
public class MatchesController extends AbstractController {
    private static class MatchActionObject {
        private Match match;
        private String actions;

        public Match getMatch() {
            return match;
        }

        public void setMatch(Match match) {
            this.match = match;
        }

        public String getActions() {
            return actions;
        }

        public void setActions(String actions) {
            this.actions = actions;
        }
    }

    @Autowired
    private MatchesService matchesService;

    @Autowired
    private TeamService teamService;

    @Autowired
    SeasonService seasonService;

    @Autowired
    AccountService accountService;

    @Autowired
    private CreateMatchValidator validator;

    @InitBinder("form")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }


    private static final Logger log = LoggerFactory.getLogger(MatchesController.class);
    private static final String LANDING_MATCHES_PAGE = "/matches/matches";
    private static final String LANDING_MATCHES_CREATE = "/matches/createMatch";


    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "createMatch", method = RequestMethod.GET)
    public String newMatch(ModelMap model, @ModelAttribute("form") CreateMatchForm form) {
        populateCreateMatch(model);
        return LANDING_MATCHES_CREATE;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "createMatch", method = RequestMethod.POST)
    public String createMatch(@ModelAttribute("form") @Valid CreateMatchForm form, BindingResult result, ModelMap model) {
        try {
            if (result.hasErrors()) {
                populateCreateMatch(model);
                return LANDING_MATCHES_CREATE;
            }
            Match m = matchesService.createMatch(form);
            log.info(String.format("Match id %s created by user %s", m.getId(), getAccountFromSecurity().getUsername()));
            return getRedirect(LANDING_MATCHES_PAGE);
        } catch (ParseException e) {
            log.debug(e.getMessage());
            return null;
        }
    }


    @RequestMapping(value = "matches", method = RequestMethod.GET)
    public String getMatchesPage(ModelMap model) {
        model.addAttribute("seasons", seasonService.getSeasons());
        return LANDING_MATCHES_PAGE;

    }

    @RequestMapping(value = "deleteMatch", method = RequestMethod.GET)
    public String deleteMatchPage(@RequestParam Long matchId, RedirectAttributes redirectAttributes, Locale locale) {
        Match m = matchesService.getMatch(matchId);
        if (m == null) throw new ObjectNotFoundException(String.format("Match id %s not found", matchId));
        matchesService.deleteMatch(matchId);
        setSuccessMessage(redirectAttributes, locale, "text.delete.match.success", null);
        log.info(String.format("Match id %s deleted by user %s", matchId, getAccountFromSecurity().getUsername()));
        return getRedirect(LANDING_MATCHES_PAGE);
    }

    @RequestMapping(value = "matchesForSeason.json", method = RequestMethod.GET)
    public
    @ResponseBody
    List<ActionWrapper<Match>> getMatchesForSeason(@RequestParam long seasonId, Locale locale) {
        Account account = getAccountFromSecurity();
        return matchesService.getMatchesForSeason(seasonId, account, locale);
    }

    private void populateCreateMatch(ModelMap model) {
        model.addAttribute("teams", teamService.getAll());
        model.addAttribute("seasons", seasonService.getSeasons());
    }

}
