package be.spring.spring.service;

import be.spring.spring.interfaces.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/**
 * Created by u0090265 on 9/11/14.
 */
@Service
public class MailServiceImpl  implements MailService {

@Autowired
private MailSender mailSender;

@Autowired
private SimpleMailMessage preConfiguredMessage;

    private static final Logger log = LoggerFactory.getLogger(MailService.class);
    /**
     * This method will send compose and send the message
     * */
    @Override
     public void sendMail(String to, String subject, String body) {
        log.debug(String.format("Trying to send message %s to %s", subject, to ));
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        try {
            mailSender.send(message);
            log.debug(String.format("Sending message to %s succeeded", to));
        } catch (Exception e) {
            log.debug(String.format("Sending message to %s failed", to));
        }

    }

    /**
     * This method will send a pre-configured message
     * */

    @Override
    public void sendPreConfiguredMail(String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage(preConfiguredMessage);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }
}
