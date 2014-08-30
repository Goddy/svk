package be.spring.spring.controller;

import be.spring.spring.form.ChangeResultForm;
import be.spring.spring.interfaces.AccountService;
import be.spring.spring.interfaces.MatchesService;
import be.spring.spring.model.Account;
import be.spring.spring.model.Match;
import be.spring.spring.validators.ResultValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.rmi.AccessException;
import java.util.List;
import java.util.Locale;

/**
 * Created by u0090265 on 5/30/14.
 */
@Controller
@RequestMapping("/matches")
public class MatchResultController extends AbstractController{

    @Autowired
    private MatchesService matchesService;

    @Autowired
    private AccountService accountService;

    @Autowired
    ResultValidator validator;

    @InitBinder("form")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    private static final String LANDING_MATCHES_CREATE_RESULT = "/matches/changeMatchResult";
    private static final String DEFAULT_TEAM = "SVK";

    @ModelAttribute("players")
    public List<Account> getPerson(){
        return accountService.getAll();
    }

    @ModelAttribute("defaultTeam")
    public String getDefaultTeam() {
        return DEFAULT_TEAM;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "changeMatchResult", method = RequestMethod.POST)
    public String postChangeMatchResult(@Valid @ModelAttribute("form") ChangeResultForm form, BindingResult result, ModelMap model ,Locale locale) {
        if (result.hasErrors()) return LANDING_MATCHES_CREATE_RESULT;
        Match m = matchesService.updateMatchResult(form);
        model.addAttribute("match", m);
        setSuccessMessage(model, locale, "text.match.result.update.success", new Object[]{m.getDescription()});
        return LANDING_MATCHES_CREATE_RESULT;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "changeMatchResult", method = RequestMethod.GET)
    public ModelAndView newMatchResult(ModelMap model, @RequestParam String id, @ModelAttribute("form") ChangeResultForm form) {
        Match match = matchesService.getMatch(Long.parseLong(id));
        model.addAttribute("match", match);

        if (match != null) {
            ChangeResultForm resultForm = new ChangeResultForm();
            resultForm.setAtGoals(match.getAtGoals());
            resultForm.setHtGoals(match.getHtGoals());
            resultForm.setMatchId(match.getId());
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
