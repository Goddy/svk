package be.spring.app.service;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.MediaType;
import java.util.Map;
import java.util.Set;

/**
 * Created by u0090265 on 12/08/16.
 */
@Qualifier("mailGunService")
@Service
public class MailGunMailServiceImpl implements MailService {
    @Value("${mail.admin.fromTo}")
    private String defaultAdminFromTo;

    @Value("${mail.admin.name}")
    private String defaultAdminName;

    @Value("${mail.admin.subject}")
    private String defaultAdminSubject;

    @Value("${mailgun.api.key}")
    private String apiKey;

    @Value("${mailgun.api.url}")
    private String apiUrl;

    private WebResource defaultMessageWebResource;

    @PostConstruct
    private void create() {
        Client client = Client.create();
        client.addFilter(new HTTPBasicAuthFilter("api",
                apiKey));
        defaultMessageWebResource =
                client.resource(apiUrl +
                        "/messages");
    }

    private boolean sendMessage(Map<String, String> to , String subject, String body) {
        MultivaluedMapImpl formData = new MultivaluedMapImpl();
        formData.add("from", String.format("%s <%s>", defaultAdminName, defaultAdminFromTo));
        formData.add("subject", subject);
        formData.add("html", body);

        for (Map.Entry<String,String> entry : to.entrySet()) {

            formData.add("to", String.format("%s <%s>", entry.getValue(), entry.getKey().isEmpty() ? entry.getValue() : entry.getKey()));
        }

        ClientResponse c =  defaultMessageWebResource.type(MediaType.APPLICATION_FORM_URLENCODED).
                post(ClientResponse.class, formData);
        return c.getStatus() == 200;
    }

    @Override
    public boolean sendMail(String to, String name, String from, String subject, String body) {
        return sendMessage(ImmutableMap.of(to, name), subject, body);
    }

    @Override
    public boolean sendMail(String to, String subject, String body) {
        return sendMessage(ImmutableMap.of(to, ""), subject, body);
    }

    @Override
    public boolean sendMail(String to, String name, String subject, String body) {
        return sendMessage(ImmutableMap.of(to, name), subject, body);
    }

    @Override
    public boolean sendMail(Set<String> to, String subject, String body) {
        Map<String, String> m = Maps.newHashMap();
        for (String t : to) {
            m.put(t, "");
        }
        return sendMessage(m, subject, body);
    }

    @Override
    public boolean sendPreConfiguredMail(String message) {
        return sendMessage(ImmutableMap.of(defaultAdminFromTo, defaultAdminName), defaultAdminSubject, message);
    }


}
