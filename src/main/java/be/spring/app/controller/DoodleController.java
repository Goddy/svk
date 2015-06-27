package be.spring.app.controller;

import be.spring.app.service.AccountService;
import be.spring.app.service.DoodleService;
import be.spring.app.service.MatchesService;
import be.spring.app.service.SeasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by u0090265 on 10/1/14.
 */
@Controller
@RequestMapping("/")
public class DoodleController extends AbstractController {
    @Autowired
    DoodleService doodleService;

    @Autowired
    AccountService accountService;

    @Autowired
    MatchesService matchesService;

    @Autowired
    SeasonService seasonService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/doodle/changeMatchDoodle", method = RequestMethod.GET)
    public @ResponseBody String getRegistrationOk(@RequestParam long matchId, @RequestParam boolean present ) {
        return doodleService.changeMatchPresence(getAccountFromSecurity(), matchId, present);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/doodle/changePresence.json", method = RequestMethod.POST)
    public String changeMatchDoodle(@RequestParam long id, @RequestParam long matchId, boolean showUsers, ModelMap map) {
        doodleService.changePresence(getAccountFromSecurity(), id, matchId).isPresent();
        map.addAttribute("accounts", accountService.getAll());
        map.addAttribute("match", matchesService.getMatch(matchId));
        map.addAttribute("currentAccount", getAccountFromSecurity());
        map.addAttribute("showUsers", showUsers);
        return "/jspf/matchDoodle";
    }


    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/doodle", method = RequestMethod.GET)
    public String getOverView(ModelMap map) {
        //Season s = seasonService.getLatestSeason();
        map.addAttribute("accounts", accountService.getAll());
        map.addAttribute("matches", matchesService.getUpcomingMatchesList());
        map.addAttribute("currentAccount", getAccountFromSecurity());
        return "/doodle/doodle";
    }
}
