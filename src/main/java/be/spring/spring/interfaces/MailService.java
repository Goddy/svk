package be.spring.spring.interfaces;

/**
 * Created by u0090265 on 9/11/14.
 */
public interface MailService {
    void sendMail(String to, String subject, String body);

    void sendPreConfiguredMail(String message);
}
