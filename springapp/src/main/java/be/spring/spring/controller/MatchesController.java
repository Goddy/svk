package be.spring.spring.controller;

import be.spring.spring.interfaces.MatchesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by u0090265 on 5/3/14.
 */
@Controller
@RequestMapping("/matches")
public class MatchesController extends AbstractController {
    @Autowired
    private MatchesService matchesService;

    private static final Logger log = LoggerFactory.getLogger(MatchesController.class);
    private static final String LANDING_MACTHES_PAGE = "/matches/matches";

    @RequestMapping(value = "matches", method = RequestMethod.GET)
    public String getAllMatches(Model model) {
        log.debug("Getting all matches");
        model.addAttribute("matches", matchesService.getMatchesForLastSeasons());
        return LANDING_MACTHES_PAGE;
    }


}
