package be.spring.app.controller;

import be.spring.app.form.AccountProfileForm;
import be.spring.app.form.ChangePwdForm;
import be.spring.app.model.Account;
import be.spring.app.model.AccountProfile;
import be.spring.app.model.Address;
import be.spring.app.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

/**
 * Created by u0090265 on 12/10/15.
 */
public abstract class AbstractAccountController extends AbstractController {
    @Autowired
    AccountService accountService;

    protected void setAccountDetailsModel(Account account, Model model, AccountProfileForm accountProfileForm, ChangePwdForm passwordForm) {
        Account currentAccount = accountService.getAccount(account.getId());
        accountProfileForm.setFirstName(currentAccount.getFirstName());
        accountProfileForm.setUsername(currentAccount.getUsername());
        accountProfileForm.setLastName(currentAccount.getLastName());
        accountProfileForm.setHasSignInProvider(currentAccount.getSignInProvider() != null);
        accountProfileForm.setHasPassword(!accountService.passwordIsNullOrEmpty(currentAccount));
        accountProfileForm.setDoodleNotificationMails(currentAccount.getAccountSettings().isSendDoodleNotifications());
        accountProfileForm.setNewsNotificationMails(currentAccount.getAccountSettings().isSendNewsNotifications());

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
        model.addAttribute("changePassword", passwordForm);
    }
}
