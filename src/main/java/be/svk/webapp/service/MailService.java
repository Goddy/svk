package be.svk.webapp.service;

import java.util.Set;

/**
 * Created by u0090265 on 9/11/14.
 */
public interface MailService {
    boolean sendMail(String to, String name, String from, String subject, String body);

    boolean sendMail(String to, String subject, String body);

    boolean sendMail(String to, String name, String subject, String body);

    boolean sendMail(Set<String> to, String subject, String body);

    boolean sendPreConfiguredMail(String message);
}
