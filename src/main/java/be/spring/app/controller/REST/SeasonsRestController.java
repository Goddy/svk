package be.spring.app.controller.REST;

import be.spring.app.dto.SeasonDTO;
import be.spring.app.dto.helper.ConversionHelper;
import be.spring.app.service.SeasonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by u0090265 on 08/07/16.
 */
@org.springframework.web.bind.annotation.RestController
@Api(value = "Seasons REST api", description = "Season REST operations")
public class SeasonsRestController extends AbstractRestController {

    @Autowired
    SeasonService seasonService;

    @Autowired
    private ConversionHelper conversionHelper;

    @RequestMapping(value = "/seasons", method = RequestMethod.GET)
    @ApiOperation(value = "Get all seasons")
    public ResponseEntity<List<SeasonDTO>> getSeasons() {
        return new ResponseEntity<>(conversionHelper.convertSeasons(seasonService.getSeasons()), HttpStatus.OK);
    }
}
