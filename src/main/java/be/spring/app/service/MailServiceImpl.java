package be.spring.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

/**
 * Created by u0090265 on 9/11/14.
 */
@Service
public class MailServiceImpl  implements MailService {
    @Autowired
    private JavaMailSenderImpl mailSender;

    @Value("${mail.admin.fromTo}")
    private String defaultAdminFromTo;

    @Value("${mail.admin.subject}")
    private String defaultAdminSubject;

    private static final Logger log = LoggerFactory.getLogger(MailService.class);
    /**
     * This method will send compose and send the message
     * */
    @Override
     public boolean sendMail(String to, String subject, String body) {
        log.debug(String.format("Trying to send message %s to %s", subject, to ));
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();

        try {
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, false, "utf-8");
            mimeMessage.setContent(body, "text/html");
            message.setTo(to);
            message.setSubject(subject);

            this.mailSender.send(mimeMessage);
            log.debug(String.format("Sending message to %s succeeded", to));
            return true;
        } catch (Exception e) {
            log.debug(String.format("Sending message to %s failed", to));
            return false;
        }

    }

    /**
     * This method will send a pre-configured message
     * */

    @Override
    public void sendPreConfiguredMail(String message) {
        sendMail(defaultAdminFromTo, defaultAdminSubject, message);
    }
}
