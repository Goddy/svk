package be.spring.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the polls.
 */
@Controller
@RequestMapping("/")
public class PollController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(PollController.class);

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/matchPolls", method = RequestMethod.GET)
    public String getMatchPoll() {
        return "/poll/matchPolls";
    }
}
