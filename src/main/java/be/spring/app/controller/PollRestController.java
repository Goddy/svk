package be.spring.app.controller;

import be.spring.app.controller.exceptions.ObjectNotFoundException;
import be.spring.app.dto.AccountDTO;
import be.spring.app.dto.MatchPollDTO;
import be.spring.app.dto.MultipleChoiceVoteDTO;
import be.spring.app.dto.VotesDTO;
import be.spring.app.dto.helper.ConversionHelper;
import be.spring.app.model.Match;
import be.spring.app.service.MatchesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

/**
 * Created by u0090265 on 10/2/15.
 */
@org.springframework.web.bind.annotation.RestController
@Api(value = "Poll REST api", description = "Poll REST operations")
public class PollRestController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(PollRestController.class);

    @Autowired
    private MatchesService matchesService;

    @Autowired
    private ConversionHelper conversionHelper;

    @RequestMapping(value = "/api/v1/match/{id}/poll", method = RequestMethod.GET)
    @ApiOperation(value = "Get poll for match", nickname = "matchpoll")
    public ResponseEntity<MatchPollDTO> getMatchPoll(@PathVariable Long id) {
        Match m = matchesService.getMatch(id);
        if (m == null) throw new ObjectNotFoundException(String.format("Match with id %s not found", id));
        return new ResponseEntity<>(conversionHelper.convertMatchPoll(m.getMotmPoll(), isLoggedIn()), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/api/v1/match/poll/{id}/vote", method = RequestMethod.POST)
    public ResponseEntity<VotesDTO> postMatchPoll(@PathVariable Long id, @RequestBody MultipleChoiceVoteDTO<Long>
            vote) {
        logger.debug(vote.toString());
        return new ResponseEntity<>(new VotesDTO(new AccountDTO(), 1), HttpStatus.OK);
    }
}
