package be.spring.spring.controller;

import be.spring.spring.form.CreateMatchForm;
import be.spring.spring.interfaces.MatchesService;
import be.spring.spring.interfaces.TeamService;
import be.spring.spring.model.Match;
import be.spring.spring.validators.CreateMatchValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Created by u0090265 on 5/3/14.
 */
@Controller
@RequestMapping("/matches")
public class MatchesController extends AbstractController {
    @Autowired
    private MatchesService matchesService;

    @Autowired
    private TeamService teamServiceImpl;

    @Autowired
    private CreateMatchValidator validator;

    @InitBinder("form")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    private static final Logger log = LoggerFactory.getLogger(MatchesController.class);
    private static final String LANDING_MACTHES_PAGE = "/matches/matches";
    private static final String LANDING_MACTHES_CREATE = "/matches/createMatch";

    @RequestMapping(value = "matchesPerSeason.json", method = RequestMethod.GET)
    public
    @ResponseBody
    Map<Integer, List<Match>> getAllMatches() {
        log.debug("Getting all matches");
        return matchesService.getMatchesForLastSeasons();
    }

    @RequestMapping(value = "createMatch", method = RequestMethod.GET)
    public String newMatch(ModelMap model, @ModelAttribute("form") CreateMatchForm form) {
        populateForm(model);
        return LANDING_MACTHES_CREATE;
    }

    @RequestMapping(value = "createMatch", method = RequestMethod.POST)
    public String createMatch(@ModelAttribute("form") @Valid CreateMatchForm form, BindingResult result, ModelMap model) {
        try {
            if (result.hasErrors()) {
                populateForm(model);
                return LANDING_MACTHES_CREATE;
            }
            matchesService.createMatch(form);
            return LANDING_MACTHES_PAGE;
        } catch (ParseException e) {
            log.debug(e.getMessage());
            return null;
        }
    }

    private void populateForm(ModelMap model) {
        model.addAttribute("teams", teamServiceImpl.getAll());
        model.addAttribute("seasons", matchesService.getSeasons());
    }

}
