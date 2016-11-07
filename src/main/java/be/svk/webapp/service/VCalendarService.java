package be.svk.webapp.service;

import net.fortuna.ical4j.model.Calendar;

import java.net.SocketException;

/**
 * Created by u0090265 on 1/26/15.
 */
public interface VCalendarService {
    Calendar getMatchesCalendar(long seasonId) throws SocketException;
}
