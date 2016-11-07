package be.svk.webapp.controller;

import be.svk.webapp.form.AccountProfileForm;
import be.svk.webapp.form.ChangePwdForm;
import be.svk.webapp.model.Account;
import be.svk.webapp.model.AccountProfile;
import be.svk.webapp.model.Address;
import be.svk.webapp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

/**
 * Created by u0090265 on 12/10/15.
 */
public abstract class AbstractAccountController extends AbstractController {
    @Autowired
    AccountService accountService;

    protected void setAccountDetailsModel(Account account, Model model, AccountProfileForm accountProfileForm, ChangePwdForm passwordForm) {
        setAccountDetailsModel(account, model, accountProfileForm);
        model.addAttribute("accountProfileForm", accountProfileForm);
        model.addAttribute("changePassword", passwordForm);
    }

    protected void setAccountDetailsModel(Account account, Model model, AccountProfileForm accountProfileForm) {
        accountProfileForm.setFirstName(account.getFirstName());
        accountProfileForm.setUsername(account.getUsername());
        accountProfileForm.setLastName(account.getLastName());
        accountProfileForm.setHasSignInProvider(account.getSignInProvider() != null);
        accountProfileForm.setHasPassword(!accountService.passwordIsNullOrEmpty(account));
        accountProfileForm.setDoodleNotificationMails(account.getAccountSettings().isSendDoodleNotifications());
        accountProfileForm.setNewsNotificationMails(account.getAccountSettings().isSendNewsNotifications());

        if (account.getAccountProfile() != null) {
            AccountProfile accountProfile = account.getAccountProfile();
            accountProfileForm.setMobilePhone(accountProfile.getMobilePhone());
            accountProfileForm.setPhone(accountProfile.getPhone());
            accountProfileForm.setPosition(accountProfile.getFavouritePosition());
            accountProfileForm.setAvatarUrl(accountProfile.getAvatar() != null ? accountProfile.getAvatar().getImageUrl() : null);

            if (accountProfile.getAddress() != null) {
                Address address = accountProfile.getAddress();
                accountProfileForm.setAddress(address.getAddress());
                accountProfileForm.setCity(address.getCity());
                accountProfileForm.setPostalCode(Integer.toString(address.getPostalCode()));
            }
        }

        model.addAttribute("accountProfileForm", accountProfileForm);
    }
}
