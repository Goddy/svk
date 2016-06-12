package be.spring.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the polls.
 */
@Controller
@RequestMapping("/poll")
public class PollController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(PollController.class);

    /**
     * Simply selects the home view to render by returning its name.
     */
    @RequestMapping(value = "/matchPoll", method = RequestMethod.GET)
    public String getMatchPoll() {
        return "/poll/matchPoll";
    }
}
