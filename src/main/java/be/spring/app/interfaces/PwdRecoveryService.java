package be.spring.app.interfaces;

import org.springframework.validation.Errors;

import java.util.Locale;

/**
 * Created by u0090265 on 9/11/14.
 */

public interface PwdRecoveryService {
    void setRecoveryCodeAndEmail(String email, Errors errors, Locale locale);

    void checkPwdRecoverCodeAndEmail(String password, String email, String code, Errors errors, Locale locale);
}
