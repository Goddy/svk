package be.spring.spring.controller;

import be.spring.spring.form.ChangeResultForm;
import be.spring.spring.interfaces.AccountService;
import be.spring.spring.interfaces.MatchesService;
import be.spring.spring.model.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.rmi.AccessException;

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

    @RequestMapping(value = "changeMatchResult.json", method = RequestMethod.POST)
    @ResponseBody public String postChangeMatchResult(@ModelAttribute("form") ChangeResultForm form, ModelMap model) {
        return form.toString();
    }

    @RequestMapping(value = "changeMatchResult", method = RequestMethod.GET)
    public ModelAndView newMatch(ModelMap model, @RequestParam String id, @ModelAttribute("form") ChangeResultForm form) {
        Match match = matchesService.getMatch(Long.parseLong(id));
        model.addAttribute("match", match);
        model.addAttribute("players", accountService.getAll());
        model.addAttribute("defaultTeam", DEFAULT_TEAM);
        model.addAttribute("update", match != null);

        if (match != null) {
            ChangeResultForm resultForm = new ChangeResultForm();
            resultForm.setAtGoals(match.getAtGoals());
            resultForm.setHtGoals(match.getHtGoals());
            model.addAttribute("form", resultForm);
        }

        return new ModelAndView(LANDING_MATCHES_CREATE_RESULT);
    }
    @RequestMapping(value = "getError", method = RequestMethod.GET)
    public void getError() throws AccessException {
        try {
            if (true) throw new AccessException("test");
        }
        finally {

        }
    }


}
