package cn.daycode.fatalism.uaa.domain;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;


public class IntegrationWebAuthenticationDetails extends WebAuthenticationDetails {

    private final String domain;

    private final String key;

    private final String authenticationType;

    public IntegrationWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        domain = request.getParameter("domain");
        key = request.getParameter("key");
        authenticationType = request.getParameter("authenticationType");
    }


    public String getDomain() {
        return domain;
    }

    public String getKey() {
        return key;
    }

    public String getAuthenticationType() {
        return authenticationType;
    }
}
