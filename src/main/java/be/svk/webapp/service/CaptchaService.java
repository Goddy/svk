package be.svk.webapp.service;

import net.tanesha.recaptcha.ReCaptchaResponse;

import javax.servlet.ServletRequest;

/**
 * Created by u0090265 on 12/31/14.
 */
public interface CaptchaService {
    String getPrivateKey();

    String getPublicKey();

    ReCaptchaResponse checkResponse(ServletRequest request, String challangeField, String responseField);
}
