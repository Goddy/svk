package be.svk.webapp.controller;

import be.svk.webapp.data.MatchStatusEnum;
import be.svk.webapp.form.ChangeResultForm;
import be.svk.webapp.model.Account;
import be.svk.webapp.model.Match;
import be.svk.webapp.model.Season;
import be.svk.webapp.model.Team;
import be.svk.webapp.service.AccountService;
import be.svk.webapp.service.MatchesService;
import be.svk.webapp.service.SeasonService;
import be.svk.webapp.service.TeamService;
import be.svk.webapp.utils.Constants;
import be.svk.webapp.validators.ChangeMatchValidator;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.rmi.AccessException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Created by u0090265 on 5/30/14.
 */
@Controller
@RequestMapping("/")
public class ChangeMatchController extends AbstractController {

    private static final String DEFAULT_TEAM = "SVK";
    @Autowired
    ChangeMatchValidator validator;
    @Autowired
    private MatchesService matchesService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private SeasonService seasonService;
    @Autowired
    private TeamService teamService;

    @InitBinder("form")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @ModelAttribute("players")
    public List<Account> getPerson() {
        List<Account> accounts = accountService.getAllActivateAccounts();
        Collections.sort(accounts);
        return accounts;
    }

    @ModelAttribute("matchStatus")
    public List<String> getMatchStatus() {
        return Lists.newArrayList(MatchStatusEnum.NOT_PLAYED.name(),
                MatchStatusEnum.PLAYED.name(),
                MatchStatusEnum.CANCELLED.name());
    }

    @ModelAttribute("defaultTeam")
    public String getDefaultTeam() {
        return DEFAULT_TEAM;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "changeMatch", method = RequestMethod.POST)
    public String postChangeMatchResult(@Valid @ModelAttribute("form") ChangeResultForm form, BindingResult result, Model model, Locale locale, final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            //Add match, otherwise information is lost
            model.addAttribute("match", matchesService.getMatch(form.getMatchId()));
            return Constants.LANDING_MATCHES_CHANGE_MATCH;
        }
        Match m = matchesService.updateMatch(form);
        setFlashSuccessMessage(redirectAttributes, locale, "text.match.result.update.success", new Object[]{m.getDescription()});
        return Constants.REDIRECT_MATCHES_PAGE;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "changeMatch", method = RequestMethod.GET)
    public ModelAndView newMatchResult(ModelMap model, @RequestParam long matchId, @ModelAttribute("form") ChangeResultForm form) {
        Match match = matchesService.getMatch(matchId);
        model.addAttribute("match", match);

        if (match != null) {
            ChangeResultForm resultForm = new ChangeResultForm();
            resultForm.setAtGoals(match.getAtGoals());
            resultForm.setHtGoals(match.getHtGoals());
            resultForm.setMatchId(match.getId());
            resultForm.setDate(match.getDate());
            resultForm.setStatus(match.getStatus());
            resultForm.setSeason(match.getSeason().getId());
            resultForm.setAwayTeam(match.getAwayTeam().getId());
            resultForm.setHomeTeam(match.getHomeTeam().getId());
            resultForm.setStatusText(match.getStatusText());
            model.addAttribute("form", resultForm);
        }

        return new ModelAndView(Constants.LANDING_MATCHES_CHANGE_MATCH);
    }

    @ModelAttribute("teams")
    public List<Team> getAllTeams() {
        return teamService.getAll();
    }

    @ModelAttribute("seasons")
    public List<Season> getAllSeasons() {
        return seasonService.getSeasons();
    }

    @RequestMapping(value = "getError", method = RequestMethod.GET)
    public void getError() throws AccessException {
        try {
            if (true) throw new AccessException("test");
        } finally {

        }
    }


}
