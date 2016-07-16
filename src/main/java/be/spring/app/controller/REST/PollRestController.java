package be.spring.app.controller.REST;

import be.spring.app.dto.AccountDTO;
import be.spring.app.dto.MatchPollDTO;
import be.spring.app.dto.MultipleChoiceVoteDTO;
import be.spring.app.model.MultipleChoicePlayerVote;
import be.spring.app.model.PlayersPoll;
import be.spring.app.service.DTOConversionHelper;
import be.spring.app.service.PollService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Set;

/**
 * Created by u0090265 on 10/2/15.
 */
@org.springframework.web.bind.annotation.RestController
@Api(value = "Poll REST api", description = "Poll REST operations")
public class PollRestController extends AbstractRestController {
    private static final Logger logger = LoggerFactory.getLogger(PollRestController.class);

    @Autowired
    private PollService pollService;

    @Autowired
    private DTOConversionHelper DTOConversionHelper;

    @PreAuthorize("hasRole('admin')")
    @RequestMapping(value = "/poll/{id}/reset", method = RequestMethod.POST)
    public ResponseEntity resetPoll(@PathVariable Long id) {
        pollService.reset(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('admin')")
    @RequestMapping(value = "/matchPoll/{id}/refresh", method = RequestMethod.POST)
    public ResponseEntity<Set<AccountDTO>> refreshMatchPoll(@PathVariable Long id) {
        return new ResponseEntity<>(DTOConversionHelper.convertIdentityOptions(pollService.refreshPlayerOptions(id), isLoggedIn()), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/matchPoll/{id}", method = RequestMethod.GET)
    public ResponseEntity<MatchPollDTO> getMatchPoll(@PathVariable Long id) {
        return new ResponseEntity<>(DTOConversionHelper.convertMatchPoll((PlayersPoll) pollService.get(id), isLoggedIn()), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/matchPoll/{id}/vote", method = RequestMethod.POST)
    public ResponseEntity<MultipleChoiceVoteDTO<Long>> postMatchPoll(@PathVariable Long id, @RequestBody
    MultipleChoiceVoteDTO<Long>
            vote) {
        logger.debug(vote.toString());
        pollService.vote(id, new MultipleChoicePlayerVote(getAccountFromSecurity(), vote.getAnswer()));
        vote.setAccount(getAccountFromSecurity());
        return new ResponseEntity<>(vote, HttpStatus.OK);
    }
}
