package be.spring.app.controller;

import be.spring.app.service.VCalendarService;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.ValidationException;
import org.hsqldb.lib.StringInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
        InputStream is = new StringInputStream(calendar.toString());
        // copy it to response's OutputStream
        FileOutputStream fOut = new FileOutputStream("mycalendar.ics");

        CalendarOutputter outPutter = new CalendarOutputter();
        outPutter.output(calendar, response.getOutputStream());

        response.setContentType("application/ics");
        response.setHeader("Content-Disposition", "attachment; filename=svk.ics");
        response.flushBuffer();
    }
}
