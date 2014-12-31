package be.spring.app.service;

import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;

/**
 * Created by u0090265 on 12/31/14.
 */
@Service
public class CatchPaServiceImpl implements CatchPaService {
    @Value("${recatchpa.private}")
    private String privateKey;

    @Value("${recatchpa.public}")
    private String publicKey;

    @Autowired
    ReCaptcha reCaptcha;

    @Override
    public String getPrivateKey() {
        return privateKey;
    }

    @Override
    public String getPublicKey() {
        return publicKey;
    }

    @Override
    public ReCaptchaResponse checkResponse(ServletRequest request, String challangeField, String responseField) {
        return this.reCaptcha.checkAnswer(request.getRemoteAddr(), challangeField, responseField);
    }
}
