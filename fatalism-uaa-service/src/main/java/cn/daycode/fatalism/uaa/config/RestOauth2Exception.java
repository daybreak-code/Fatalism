package cn.daycode.fatalism.uaa.config;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

@JsonSerialize(using = RestOauthExceptionJacksonSerializer.class)
public class RestOauth2Exception extends OAuth2Exception {
    public RestOauth2Exception(String msg, Throwable t) {
        super(msg, t);
    }

    public RestOauth2Exception(String msg) {
        super(msg);
    }
}
