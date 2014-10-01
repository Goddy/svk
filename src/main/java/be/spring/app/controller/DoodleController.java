package be.spring.app.controller;

import be.spring.app.service.DoodleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by u0090265 on 10/1/14.
 */
@Controller
@RequestMapping("/doodle")
public class DoodleController extends AbstractController {
    @Autowired
    DoodleService doodleService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "changeMatchDoodle", method = RequestMethod.GET)
    public @ResponseBody String getRegistrationOk(@RequestParam long matchId, @RequestParam boolean present ) {
        return doodleService.changeMatchPresence(getAccountFromSecurity(), matchId, present);
    }
}
