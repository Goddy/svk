package be.spring.app.service;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Set;

/**
 * Created by u0090265 on 9/11/14.
 * Depracated in favor of mailgun.
 */
@Deprecated
public class MailServiceImpl  implements MailService {
    @Autowired
    private JavaMailSenderImpl mailSender;

    @Value("${mail.admin.fromTo}")
    private String defaultAdminFromTo;

    @Value("${mail.admin.subject}")
    private String defaultAdminSubject;

    private static final Logger log = LoggerFactory.getLogger(MailService.class);

    @Override
    public boolean sendMail(String to, String name, String from, String subject, String body) {
        try {
            InternetAddress internetAddressTo = Strings.isNullOrEmpty(name) ? new InternetAddress(to) : new InternetAddress(to, name);
            return send(new InternetAddress[]{internetAddressTo}, from, subject, body);
        } catch (Exception e) {
            log.debug(String.format("Sending message to %s failed: Could not convert internetAddresses", to));
            return false;
        }

    }

    @Override
    public boolean sendMail(String to, String subject, String body) {
        return sendMail(to, null, defaultAdminFromTo, subject, body);
    }

    @Override
    public boolean sendMail(String to, String name, String subject, String body) {
        return sendMail(to, name, defaultAdminFromTo, subject, body);
    }

    @Override
    public boolean sendMail(Set<String> to, String subject, String body) {
        List<InternetAddress> internetAddressList = Lists.newArrayList();
        for (String s : to) {
            try {
                internetAddressList.add(new InternetAddress(s));
            } catch (Exception e) {
                log.debug(String.format("Could not convert internetAddress. Cause: %s", e.getCause()));
            }
        }

        return !internetAddressList.isEmpty() && send(internetAddressList.toArray(new InternetAddress[internetAddressList.size()]), defaultAdminFromTo, subject, body);
    }

    /**
     * This method will send a pre-configured message
     * */

    @Override
    public boolean sendPreConfiguredMail(String message) {
        return sendMail(defaultAdminFromTo, null, defaultAdminSubject, message);
    }

    private boolean send(InternetAddress[] to, String from, String subject, String body) {
        log.debug(String.format("Trying to send message %s to %s", subject, to));
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();

        try {
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessage.setContent(body, "text/html; charset=\"UTF-8\"");
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
}
