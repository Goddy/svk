package be.spring.app.controller.REST;

import be.spring.app.controller.exceptions.ObjectNotFoundException;
import be.spring.app.dto.MatchDTO;
import be.spring.app.dto.MatchPollDTO;
import be.spring.app.dto.MultipleChoiceVoteDTO;
import be.spring.app.service.DTOConversionHelper;
import be.spring.app.model.Account;
import be.spring.app.dto.ActionWrapperDTO;
import be.spring.app.model.Match;
import be.spring.app.model.MultipleChoicePlayerVote;
import be.spring.app.service.MatchesService;
import be.spring.app.service.PollService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

/**
 * Created by u0090265 on 08/07/16.
 */
@org.springframework.web.bind.annotation.RestController
@Api(value = "Matches REST api", description = "Matches REST operations")
public class MatchesRestController extends AbstractRestController {
    private static final Logger logger = LoggerFactory.getLogger(MatchesRestController.class);

    @Autowired
    private MatchesService matchesService;

    @Autowired
    private PollService pollService;

    @Autowired
    private DTOConversionHelper DTOConversionHelper;

    @RequestMapping(value = "/matches/season/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<ActionWrapperDTO<MatchDTO>> getMatchesForSeason(@PathVariable Long id, Locale locale) {
        Account account = getAccountFromSecurity();
        List<ActionWrapperDTO<MatchDTO>> r = matchesService.getMatchesWrappersForSeason(id, locale, getAccountFromSecurity());
        return r;
    }

    @RequestMapping(value = "/match/{id}/poll", method = RequestMethod.GET)
    @ApiOperation(value = "Get poll for match", nickname = "matchpoll")
    public ResponseEntity<MatchPollDTO> getMatchPoll(@PathVariable Long id) {
        Match m = matchesService.getMatch(id);
        if (m == null) throw new ObjectNotFoundException(String.format("Match with id %s not found", id));
        return new ResponseEntity<>(DTOConversionHelper.convertMatchPoll(m.getMotmPoll(), isLoggedIn()), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/match/poll/{id}/vote", method = RequestMethod.POST)
    public ResponseEntity<MultipleChoiceVoteDTO<Long>> postMatchPoll(@PathVariable Long id, @RequestBody
    MultipleChoiceVoteDTO<Long>
            vote) {
        logger.debug(vote.toString());
        pollService.vote(id, new MultipleChoicePlayerVote(getAccountFromSecurity(), vote.getAnswer()));
        vote.setAccount(getAccountFromSecurity());
        return new ResponseEntity<>(vote, HttpStatus.OK);
    }
}
