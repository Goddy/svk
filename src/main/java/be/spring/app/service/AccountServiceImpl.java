package be.spring.app.service;

import be.spring.app.controller.exceptions.ObjectNotFoundException;
import be.spring.app.form.AccountProfileForm;
import be.spring.app.form.ActivateAccountForm;
import be.spring.app.model.Account;
import be.spring.app.model.AccountProfile;
import be.spring.app.model.Address;
import be.spring.app.model.Image;
import be.spring.app.persistence.AccountDao;
import be.spring.app.utils.GeneralUtils;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    private static final String UPDATE_PASSWORD_SQL = "update account set password = ? where id = ?";
    private static final String GET_PASSWORD = "select password from account where id = ?";
    @Autowired
    MessageSource messageSource;
    @Value("${base.url}")
    private String baseUrl;
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private MailService mailService;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private ImageService imageService;

    @Override
    public Account registerAccount(Account account, String password) {
        Account resultAccount = createAccountWithPassword(account, password);
        mailService.sendPreConfiguredMail(messageSource.getMessage("mail.user.registered", new Object[]{baseUrl,
                account.getId(), account.toString()}, Locale.ENGLISH));
        return resultAccount;
    }

    @Transactional(readOnly = false)
    private Account createAccountWithPassword(Account account, String password) {
        Account resultAccount = accountDao.save(account);
        //Update only if sign in provider is not specified
        if (account.getSignInProvider() == null) {
            String encPassword = passwordEncoder.encode(password);
            jdbcTemplate.update(UPDATE_PASSWORD_SQL, encPassword, account.getId());
        }
        return resultAccount;
    }

    @Transactional(readOnly = false)
    public Account updateAccount(Account account, AccountProfileForm form) {
        //To do: why is object detached when coming from security?
        return accountDao.save(getUpdatedAccount(account, form));
    }

    @Override
    public Account saveAccount(Account account) {
        return accountDao.save(account);
    }

    @Override
    @Transactional
    public Account activateAccount(ActivateAccountForm form, Locale locale, Errors errors) {
        Account account = accountDao.findOne(form.getAccountId());
        if (account == null) throw new ObjectNotFoundException(String.format("Object with id %s not found", form.getAccountId()));
        account.setActive(true);
        if (form.isSendEmail()) {
            if (!mailService.sendMail(account.getUsername(),
                    messageSource.getMessage("email.activation.subject", null, locale),
                    messageSource.getMessage("email.activation.body", new String[]{account.getFirstName()}, locale))) {
                errors.reject("sendEmail");
            }
        }
        return account;
    }

    @Transactional(readOnly = true)
    public void validateUsername(String username, Errors errors) {
        if (accountDao.findByUsername(username) != null) {
            errors.rejectValue("username", "error.duplicate.account.email",
                    new String[]{username}, null);
        }
    }

    public void validateUsernameExcludeCurrentId(String username, Long id, Errors errors) {
        if (accountDao.findByEmailExcludeCurrentId(username, id) != null) {
            errors.rejectValue("username", "error.duplicate.account.email",
                    new String[]{username}, null);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void setPasswordFor(Account account, String password) {
        String encPassword = passwordEncoder.encode(password);
        jdbcTemplate.update(UPDATE_PASSWORD_SQL, encPassword, account.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkOldPassword(Account account, String password) {
        String encodedPassword = getCurrentEncodedPasswordFor(account);
        return !(encodedPassword == null || encodedPassword.isEmpty()) && passwordEncoder.matches(password, encodedPassword);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean passwordIsNullOrEmpty(Account account) {
        String encodedPassword = getCurrentEncodedPasswordFor(account);
        return encodedPassword == null || encodedPassword.isEmpty();
    }

    @Override
    public List<Account> getAll() {
        List<Account> r = Lists.newArrayList(accountDao.findAll());
        Collections.sort(r);
        return r;
    }

    @Override
    public List<Account> getAllActivateAccounts() {
        List<Account> r = Lists.newArrayList(accountDao.findAllByActive(true));
        Collections.sort(r);
        return r;
    }

    @Override
    public Account getAccount(String id) {
        return accountDao.findOne(GeneralUtils.convertToLong(id));
    }

    @Override
    public Account getAccount(Long id) {
        return accountDao.findOne(id);
    }

    @Override
    public Account getActiveAccountByEmail(String email) {
        return accountDao.findByUsernameAndActiveStatus(email, true);
    }

    @Override
    public Account getActiveAccountById(String id) {
        return accountDao.findByIdAndActiveStatus(GeneralUtils.convertToLong(id), true);
    }

    @Override
    public List<Account> getAccountsByActivationStatus(boolean status) {
        return accountDao.findByActivationStatus(status);
    }

    @Override
    public List<Account> getAccountsWithActivationCode() {
        return accountDao.findByActivationCodeNotNull();
    }

    private Account getUpdatedAccount(Account account, AccountProfileForm form) {
        Account newAccount = accountDao.findOne(account.getId());
        newAccount.setFirstName(form.getFirstName());
        newAccount.setLastName(form.getLastName());
        newAccount.setUsername(form.getUsername());
        setAccountProfile(account, newAccount, form);
        newAccount.getAccountSettings().setSendDoodleNotifications(form.isDoodleNotificationMails());
        newAccount.getAccountSettings().setSendNewsNotifications(form.isNewsNotificationMails());
        return newAccount;
    }

    private String getCurrentEncodedPasswordFor(Account account) {
        return jdbcTemplate.queryForObject(GET_PASSWORD, String.class, account.getId());
    }

    private void setAccountProfile(Account oldAccount, Account updatedAccount, AccountProfileForm form) {
        updatedAccount.setAccountProfile(oldAccount.getAccountProfile() == null ?
                new AccountProfile() : oldAccount.getAccountProfile());
        AccountProfile accountProfile = updatedAccount.getAccountProfile();
        //Set enum
        accountProfile.setFavouritePosition(form.getPosition());
        //Set mobile, phone, address, etc
        accountProfile.setPhone(form.getPhone());
        accountProfile.setMobilePhone(form.getMobilePhone());
        accountProfile.setAddress(createAddress(accountProfile.getAddress(), form));
        //Set image as a null value to remove, else set profile image with provided image.
        updatedAccount.getAccountProfile().setAvatar(form.isRemoveAvatar() ?
                null :
                createProfileImage(updatedAccount.getAccountProfile().getAvatar(), form.getAvatar()));
    }

    private Address createAddress(Address address, AccountProfileForm form) {
        if (!Strings.isNullOrEmpty(form.getAddress()) && !Strings.isNullOrEmpty(form.getPostalCode()) && !Strings.isNullOrEmpty(form.getCity())) {
            if (address == null) address = new Address();
            address.setAddress(form.getAddress());
            address.setCity(form.getCity());
            address.setPostalCode(Integer.parseInt(form.getPostalCode()));
        }
        return address;
    }

    private Image createProfileImage(Image image, MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            try {
                return imageService.uploadProfileImage(file);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("Could not upload image");
                //Fail if image cannot be uploaded
                throw new RuntimeException(e.getMessage(), e.getCause());
            }
        } else {
            return image;
        }
    }
}
