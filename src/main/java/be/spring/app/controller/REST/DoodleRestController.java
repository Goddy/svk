package be.spring.app.controller.REST;

import be.spring.app.dto.MatchDoodleDTO;
import be.spring.app.dto.PresenceDTO;
import be.spring.app.model.Match;
import be.spring.app.service.DTOConversionHelper;
import be.spring.app.service.DoodleService;
import be.spring.app.service.MatchesService;
import com.google.common.base.Optional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by u0090265 on 09/09/16.
 */
@org.springframework.web.bind.annotation.RestController
@Api(value = "Doodle REST api", description = "Doodle REST operations")
public class DoodleRestController extends AbstractRestController {
    private MatchesService matchesService;
    private DTOConversionHelper DTOConversionHelper;
    private DoodleService doodleService;

    @Autowired
    public DoodleRestController(MatchesService matchesService, DTOConversionHelper dtoConversionHelper, DoodleService
            doodleService) {
        this.matchesService = matchesService;
        this.DTOConversionHelper = dtoConversionHelper;
        this.doodleService = doodleService;
    }

    @RequestMapping(value = "/matchDoodles/list", method = RequestMethod.GET)
    @ApiOperation(value = "Get matchdoodles", nickname = "matchdoodles")
    public ResponseEntity<List<MatchDoodleDTO>> getMatchDoodles(@RequestParam int page, @RequestParam(required =
            false) int size) {
        Page<Match> matches = matchesService.getUpcomingMatchesPages(page, size, Optional.<Sort>absent());
        return new ResponseEntity<>(DTOConversionHelper.convertMatchDoodles(matches, isLoggedIn()), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/doodle/{id}/presence", method = RequestMethod.POST)
    @ApiOperation(value = "Get matchdoodles", nickname = "matchdoodles")
    public ResponseEntity<PresenceDTO> changePersence(@PathVariable Long id, @RequestBody
    PresenceDTO presence) {
        return new ResponseEntity<>(DTOConversionHelper.convertPresence(doodleService.changePresence
                (getAccountFromSecurity(), presence.getAccount().getId(), id), isLoggedIn()), HttpStatus.OK);
    }
}
