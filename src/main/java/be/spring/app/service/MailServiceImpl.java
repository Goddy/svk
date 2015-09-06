package be.spring.app.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Set;

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
    public boolean sendMail(String to, String from, String subject, String body) {
        log.debug(String.format("Trying to send message %s to %s", subject, to ));
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();

        try {
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, false, "utf-8");
            mimeMessage.setContent(body, "text/html");
            mimeMessage.setFrom(new InternetAddress(from));
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

    @Override
    public boolean sendMail(String to, String subject, String body) {
        return sendMail(to, defaultAdminFromTo, subject, body);
    }

    @Override
    public boolean sendMail(Set<String> to, String subject, String body) {
        return sendMail(StringUtils.join(to, ','), defaultAdminFromTo, subject, body);
    }

    /**
     * This method will send a pre-configured message
     * */

    @Override
    public boolean sendPreConfiguredMail(String message) {
        return sendMail(defaultAdminFromTo, defaultAdminSubject, message);
    }
}
