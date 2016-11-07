package be.svk.webapp.controller;

import be.svk.webapp.service.VCalendarService;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by u0090265 on 1/26/15.
 */
@Controller
@RequestMapping("/calendar")
public class CalendarController {
    @Autowired
    VCalendarService calendarService;

    @RequestMapping(value = "getMatchesCalendar", method = RequestMethod.GET)
    public void doDownload(@RequestParam("seasonId") long seasonId, HttpServletResponse response) throws IOException, ValidationException {
        Calendar calendar = calendarService.getMatchesCalendar(seasonId);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/calendar");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=svk.ics");
        CalendarOutputter o = new CalendarOutputter();
        o.output(calendar, response.getOutputStream());
        response.flushBuffer();
    }
}
