package be.spring.app.service;

import be.spring.app.model.Address;
import be.spring.app.model.Match;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.util.UidGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by u0090265 on 1/26/15.
 */
@Service
public class VCalendarServiceImpl implements VCalendarService {
    // Create a TimeZone
    TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
    TimeZone timezone = registry.getTimeZone("Europe/Brussels");
    VTimeZone tz = timezone.getVTimeZone();

    @Autowired
    MatchesService matchesService;

    @Override
    public Calendar getMatchesCalendar(long seasonId) throws SocketException {
        List<Match> matches = matchesService.getMatchesForSeason(seasonId);
        List<VEvent> calendarEvents = new ArrayList<>();

        // Create a calendar
        net.fortuna.ical4j.model.Calendar icsCalendar = new net.fortuna.ical4j.model.Calendar();
        icsCalendar.getProperties().add(new ProdId("-//Svk Matches//iCal4j 1.0//EN"));
        icsCalendar.getProperties().add(CalScale.GREGORIAN);
        icsCalendar.getProperties().add(Version.VERSION_2_0);
        TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
        VTimeZone tz = registry.getTimeZone("Europe/Brussels").getVTimeZone();
        icsCalendar.getComponents().add(tz);

        for (Match m : matches) {
            Address htLocation = m.getHomeTeam().getAddress();
            DateTime start = new DateTime(m.getDate().getMillis());
            DateTime end = new DateTime(m.getDate().plusHours(2).getMillis());
            VEvent e = new VEvent(start, end, m.getDescription());
            Location location = new Location(String.format("%s, %s %s", htLocation.getAddress(), htLocation.getPostalCode(), htLocation.getCity()));
            e.getProperties().add(location);
            calendarEvents.add(new VEvent());

            UidGenerator ug = new UidGenerator("uidGen");
            Uid uid = ug.generateUid();
            e.getProperties().add(uid);

            icsCalendar.getComponents().add(e);
        }

        return icsCalendar;
    }
}
