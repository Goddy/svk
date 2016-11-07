package be.svk.webapp.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by u0090265 on 10/1/14.
 */
@Controller
@RequestMapping("/")
public class DoodleController extends AbstractController {
    public static final String VN_DOODLE = "/doodle";

    @RequestMapping(value = "/doodle", method = RequestMethod.GET)
    public String getOverView(Model map) {
        return "/doodle/doodle";
    }

    @RequestMapping(value = "/getDoodle", method = RequestMethod.GET)
    public String getSingleDoodle(@RequestParam long matchId, ModelMap map) {
        map.put("matchId", matchId);
        return "/doodle/getDoodle";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/getMembersDoodle", method = RequestMethod.GET)
    public String getDoodleMembers(@RequestParam long matchId, ModelMap map) {
        map.put("matchId", matchId);
        return "/doodle/getDoodle";
    }
    
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/membersDoodle", method = RequestMethod.GET)
    public String getOverViewProtected(Model map) {
        return "/doodle/doodle";
    }
}
