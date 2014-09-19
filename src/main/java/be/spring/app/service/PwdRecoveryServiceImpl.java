package be.spring.app.service;

import be.spring.app.controller.exceptions.ObjectNotFoundException;
import be.spring.app.interfaces.AccountDao;
import be.spring.app.interfaces.MailService;
import be.spring.app.interfaces.PwdRecoveryService;
import be.spring.app.model.Account;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Created by u0090265 on 9/11/14.
 */
@Service
@Transactional
public class PwdRecoveryServiceImpl implements PwdRecoveryService {

    @Value("${base.url}")
    private String baseUrl;

    private MailService mailService;
    private AccountDao accountDao;
    private MessageSource messageSource;

    private final static String TIMESTAMP_PATTERN =  "yyyyMMddHHmmss";
    private static final int RECOVERY_LENGTH = 10;

    private static final Logger log = LoggerFactory.getLogger(PwdRecoveryServiceImpl.class);

    @Autowired
    public PwdRecoveryServiceImpl(MailService mailService, AccountDao accountDao, MessageSource messageSource) {
        this.mailService = mailService;
        this.accountDao = accountDao;
        this.messageSource = messageSource;
    }

    @Override
    @Transactional
    public void deleteExpiredCodes() {
        List<Account> accounts = accountDao.findByActivationCodeNotNull();
        DateTime hourAgo = DateTime.now().minusHours(1);

        for (Account a : accounts) {

            DateTime codeTime = getDateFromDbString(a.getPwdRecovery());
            if (codeTime.isBefore(hourAgo)) {
                a.setPwdRecovery(null);
                accountDao.update(a);
                log.info("Deleted activationcode for account {}", a.getUsername());
            }
        }
    }

    @Override
    @Transactional
    public void setRecoveryCodeAndEmail(String email, Errors errors, Locale locale) {
        Account account = accountDao.findByUsername(email);
        if (account == null) throw new ObjectNotFoundException(String.format("Account with email %s not found", email));

        String recoveryHex = getRandomHexString(RECOVERY_LENGTH);
        String pwdRecoveryCode = getTimeStamp() + recoveryHex;

        account.setPwdRecovery(pwdRecoveryCode);

        accountDao.update(account);

        Object[] args = new Object[] {account.getFirstName(), recoveryHex, account.getUsername(), baseUrl};

        if (!mailService.sendMail(
                account.getUsername(),
                messageSource.getMessage("email.pwd.recovery.subject", null , locale),
                messageSource.getMessage("email.pwd.recovery.body", args, locale))) {
            errors.rejectValue("email", "validation.email.not.sent");
        }
    }

    @Override
    @Transactional
    public void checkPwdRecoverCodeAndEmail(String password, String email, String code, Errors errors, Locale locale) {
        Account account = accountDao.findByUsername(email);
        if (account == null) throw new ObjectNotFoundException(String.format("Account with email %s not found", email));

        String dbRecoveryCode = getCodeFromDbString(account.getPwdRecovery());

        if (dbRecoveryCode == null || !dbRecoveryCode.equals(code)) {
            errors.rejectValue("code", "validation.pwd.recovery.code");
        }
        else {
            account.setPwdRecovery(null);
            accountDao.update(account, password);
        }
    }

    private String getTimeStamp() {
        DateTime dt = new DateTime();
        DateTimeFormatter fmt = DateTimeFormat.forPattern(TIMESTAMP_PATTERN);
        return fmt.print(dt);
    }
    private DateTime getDateTimeFromString(String date) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(TIMESTAMP_PATTERN);
        return formatter.parseDateTime(date);
    }

    private String getRandomHexString(int numChars){
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        while(sb.length() < numChars){
            sb.append(Integer.toHexString(r.nextInt()));
        }
        return sb.toString().substring(0, numChars);
    }

    private String getCodeFromDbString(String code) {
        if (code == null || code.length() < TIMESTAMP_PATTERN.length() + RECOVERY_LENGTH) return null;
        return code.substring(TIMESTAMP_PATTERN.length(), code.length());
    }

    private DateTime getDateFromDbString(String code) {
        if (code == null || code.length() < TIMESTAMP_PATTERN.length() + RECOVERY_LENGTH) return null;
        return getDateTimeFromString(code.substring(0, TIMESTAMP_PATTERN.length()));
    }

}
