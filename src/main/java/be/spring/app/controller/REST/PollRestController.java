package be.spring.app.controller.REST;

import be.spring.app.dto.AccountDTO;
import be.spring.app.dto.MatchPollDTO;
import be.spring.app.dto.MultipleChoiceVoteDTO;
import be.spring.app.dto.PageDTO;
import be.spring.app.model.Match;
import be.spring.app.model.MultipleChoicePlayerVote;
import be.spring.app.model.PlayersPoll;
import be.spring.app.service.DTOConversionHelper;
import be.spring.app.service.MatchesService;
import be.spring.app.service.PollService;
import com.google.common.base.Optional;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    private MatchesService matchesService;

    @Autowired
    private DTOConversionHelper DTOConversionHelper;

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/poll/{id}/reset", method = RequestMethod.PUT)
    public ResponseEntity resetPoll(@PathVariable Long id) {
        pollService.reset(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/matchPoll/match/{id}/refresh", method = RequestMethod.PUT)
    public ResponseEntity<List<AccountDTO>> refreshMatchPoll(@PathVariable Long id) {
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
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/matchPoll", method = RequestMethod.GET)
    public ResponseEntity<PageDTO<MatchPollDTO>> getAllMatchPolls(@RequestParam int page, @RequestParam(required = false) int size, @RequestParam(required = false) String sort) {
        Page<Match> playersPolls = matchesService.getMatchesWithPolls(page, size, Optional.<Sort>absent(), Optional.<String>absent());
        return new ResponseEntity<>(DTOConversionHelper.convertMatchPolls(playersPolls, isLoggedIn()), HttpStatus.OK);
    }
}
