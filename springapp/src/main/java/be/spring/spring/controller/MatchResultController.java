package be.spring.spring.controller;

import be.spring.spring.form.ChangeResultForm;
import be.spring.spring.interfaces.AccountService;
import be.spring.spring.interfaces.MatchesService;
import be.spring.spring.model.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by u0090265 on 5/30/14.
 */
@Controller
@RequestMapping("/matches")
public class MatchResultController {

    @Autowired
    private MatchesService matchesService;

    @Autowired
    AccountService accountService;

    private static final String LANDING_MATCHES_CREATE_RESULT = "/matches/changeMatchResult";
    private static final String DEFAULT_TEAM = "SVK";

    @RequestMapping(value = "changeMatchResult", method = RequestMethod.POST)
    public String postChangeMatchResult(@ModelAttribute("match") Match match, ModelMap model) {
        match.getAwayTeam();
        return "";
    }

    @RequestMapping(value = "changeMatchResult", method = RequestMethod.GET)
    public ModelAndView newMatch(ModelMap model, @RequestParam String id, @ModelAttribute("resultForm") ChangeResultForm form) {
        Match match = matchesService.getMatch(Long.parseLong(id));
        model.addAttribute("players", accountService.getAll());
        model.addAttribute("defaultTeam", DEFAULT_TEAM);
        model.addAttribute("update", false);
        ChangeResultForm resultForm = new ChangeResultForm();
        resultForm.setAtGoals("1");
        model.addAttribute("resultForm", resultForm);

        return new ModelAndView(LANDING_MATCHES_CREATE_RESULT, "command", match);
    }
}
