package be.spring.app.controller;

import be.spring.app.dto.MatchDTO;
import be.spring.app.dto.TeamDTO;
import be.spring.app.dto.helper.ConversionHelper;
import be.spring.app.service.MatchesService;
import be.spring.app.service.TeamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by u0090265 on 10/2/15.
 */
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api/v1")
@Api(value = "REST api", description = "All REST operations")
public class RestController extends AbstractController {
    @Autowired
    private MatchesService matchesService;

    @Autowired
    private ConversionHelper conversionHelper;

    @Autowired
    private TeamService teamService;

    @RequestMapping(value = "/matches/{seasonDescription}", method = RequestMethod.GET)
    @ApiOperation(value = "List all matches for a specific season", nickname = "matches")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Not found"),
    })
    public
    @ResponseBody
    List<MatchDTO> getMatchesForSeason(@PathVariable String seasonDescription) {
        return conversionHelper.convertMatches(matchesService.getMatchesForSeason(seasonDescription));
    }

    @RequestMapping(value = "/teams", method = RequestMethod.GET)
    @ApiOperation(value = "List all teams", nickname = "teams")
    public
    @ResponseBody
    List<TeamDTO> getTeams() {
        return conversionHelper.convertTeams(teamService.getAll());
    }
}