package be.spring.app.service;

import com.google.api.client.util.Value;
import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.util.AuthenticationException;

import javax.annotation.PostConstruct;

/**
 * Created by u0090265 on 11/28/15.
 */
public class PicasaServiceImpl implements PicasaService {
    @Value("${gmail.account}")
    private String username;
    @Value("${gmail.account.password}")
    private String password;
    @Value("${picasa.album}")
    private String picasaAlbumName = "private";

    private PicasawebService picasawebService;

    @PostConstruct
    public void initialize() throws AuthenticationException {
        //Start webservice -> will fail if usercredentials are faulty
        picasawebService = new PicasawebService("SVK");
        picasawebService.setUserCredentials(username, password);
    }

    //@Override
    public boolean upload() {
        //picasawebService.insert()
        return true;
    }
}
