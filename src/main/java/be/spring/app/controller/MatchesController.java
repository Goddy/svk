package be.spring.app.controller;

import be.spring.app.controller.exceptions.ObjectNotFoundException;
import be.spring.app.form.CreateMatchForm;
import be.spring.app.model.*;
import be.spring.app.service.AccountService;
import be.spring.app.service.MatchesService;
import be.spring.app.service.SeasonService;
import be.spring.app.service.TeamService;
import be.spring.app.utils.Constants;
import be.spring.app.validators.CreateMatchValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @ModelAttribute("seasons")
    public List<Season> getSeasons() {
        return seasonService.getSeasons();
    }

    @ModelAttribute("teams")
    public List<Team> getTeams() {
        return teamService.getAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "createMatch", method = RequestMethod.GET)
    public String newMatch(Model model, @ModelAttribute("form") CreateMatchForm form) {
        return Constants.LANDING_MATCHES_CREATE;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "createMatch", method = RequestMethod.POST)
    public String createMatch(@ModelAttribute("form") @Valid CreateMatchForm form, BindingResult result, Model model, Locale locale) {
        try {
            if (result.hasErrors()) {
                return Constants.LANDING_MATCHES_CREATE;
            }
            Match m = matchesService.createMatch(form);
            log.info(String.format("Match id %s created by user %s", m.getId(), getAccountFromSecurity().getUsername()));
            setSuccessMessage(model, locale, "text.match.created", new Object[]{m.getDescription()});
            return Constants.LANDING_MATCHES_CREATE;
        } catch (ParseException e) {
            log.debug(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "matches", method = RequestMethod.GET)
    public String getMatchesPage(Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("nextMatch", matchesService.getLatestMatch());
        return Constants.LANDING_MATCHES_PAGE;

    }

    @ModelAttribute(value = "loggedIn")
    public boolean setLoggedIn() {
        return isLoggedIn();
    }

    @RequestMapping(value = "deleteMatch", method = RequestMethod.GET)
    public String deleteMatchPage(@RequestParam Long matchId, RedirectAttributes redirectAttributes, Locale locale) {
        Match m = matchesService.getMatch(matchId);
        if (m == null) throw new ObjectNotFoundException(String.format("Match id %s not found", matchId));
        matchesService.deleteMatch(matchId);
        setSuccessMessage(redirectAttributes, locale, "text.delete.match.success", null);
        log.info(String.format("Match id %s deleted by user %s", matchId, getAccountFromSecurity().getUsername()));
        return getRedirect(Constants.LANDING_MATCHES_PAGE);
    }

    @RequestMapping(value = "matchesForSeason.json", method = RequestMethod.GET)
    public
    @ResponseBody
    List<ActionWrapper<Match>> getMatchesForSeason(@RequestParam long seasonId, Locale locale) {
        Account account = getAccountFromSecurity();
        List<ActionWrapper<Match>> r = matchesService.getMatchesForSeason(seasonId, account, locale);
        return r;
    }
}
