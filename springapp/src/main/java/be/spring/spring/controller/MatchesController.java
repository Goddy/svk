package be.spring.spring.controller;

import be.spring.spring.interfaces.MatchesService;
import be.spring.spring.model.Match;
import be.spring.spring.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    private TeamService teamService;

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
    public String newMatch(ModelMap model) {
        model.addAttribute("teams", teamService.getAll());
        model.addAttribute("seasons", matchesService.getSeasons());
        return LANDING_MACTHES_CREATE;
    }


}
