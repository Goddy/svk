package be.svk.webapp.controller;

import be.svk.webapp.service.CacheAdapter;
import be.svk.webapp.service.SeasonService;
import be.svk.webapp.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.ExecutionException;

/**
 * Created by u0090265 on 10/10/15.
 */
@Controller
@RequestMapping("/")
public class StatisticsController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(StatisticsController.class);

    @Autowired
    CacheAdapter cacheAdapter;

    @Autowired
    SeasonService seasonService;

    @RequestMapping(value = "statistics", method = RequestMethod.GET)
    public String getStatistics(@RequestParam(required = false) Long seasonId, ModelMap model) {
        if (seasonId == null) {
            seasonId = seasonService.getLatestSeason().getId();
        }
        try {
            model.addAttribute("stats", cacheAdapter.getStatisticsForSeason(seasonId));
            model.addAttribute("seasons", seasonService.getSeasons());
            model.addAttribute("selectedSeason", seasonId);
        } catch (ExecutionException | InterruptedException e) {
            log.error("Cannot get statistics");
            e.printStackTrace();
        }
        return Constants.LANDING_STATISTICS;
    }
}
