package be.spring.app.controller;

import be.spring.app.controller.exceptions.ObjectNotFoundException;
import be.spring.app.model.Account;
import be.spring.app.model.Match;
import be.spring.app.service.AccountService;
import be.spring.app.service.DoodleService;
import be.spring.app.service.MatchesService;
import be.spring.app.service.SeasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @ModelAttribute(value = "accounts")
    public List<Account> getActiveAccounts() {
        return accountService.getAllActivateAccounts();
    }

    @ModelAttribute(value = "currentAccount")
    public Account getCurrentAccount() {
        return getAccountFromSecurity();
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/doodle/changeMatchDoodle", method = RequestMethod.GET)
    public
    @ResponseBody
    String getChangeMatchDoodle(@RequestParam long matchId, @RequestParam boolean present) {
        return doodleService.changeMatchPresence(getAccountFromSecurity(), matchId, present);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/doodle/changePresence.json", method = RequestMethod.POST)
    public String changeMatchDoodle(@RequestParam long id, @RequestParam long matchId, boolean showUsers, ModelMap map) {
        doodleService.changePresence(getAccountFromSecurity(), id, matchId).isPresent();
        map.addAttribute("match", matchesService.getMatch(matchId));
        map.addAttribute("showUsers", showUsers);
        return "/jspf/matchDoodle";
    }


    @RequestMapping(value = "/doodle", method = RequestMethod.GET)
    public String getOverView(ModelMap map) {
        return getDoodle(map);
    }

    @RequestMapping(value = "/getDoodle", method = RequestMethod.GET)
    public String getSingleDoodle(@RequestParam long matchId, ModelMap map) {
        return getDoodle(matchId, map);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/getMembersDoodle", method = RequestMethod.GET)
    public String getDoodleMembers(@RequestParam long matchId, ModelMap map) {
        return getDoodle(matchId, map);
    }
    
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/membersDoodle", method = RequestMethod.GET)
    public String getOverViewProtected(ModelMap map) {
        return getDoodle(map);
    }

    private String getDoodle(long matchId, ModelMap map) {
        Match m = matchesService.getMatch(matchId);
        if (m == null) throw new ObjectNotFoundException(String.format("Match with id %s not found", matchId));
        map.addAttribute("match", m);
        return "/doodle/getDoodle";
    }
    private String getDoodle(ModelMap map) {
        map.addAttribute("matches", matchesService.getUpcomingMatchesList());
        return "/doodle/doodle";
    }
}
