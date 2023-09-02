package cn.daycode.fatalism.gateway.config;

import cn.daycode.fatalism.gateway.common.util.HttpUtil;
import cn.daycode.fatalism.common.domain.RestResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;

import java.io.IOException;

public class RestOAuth2AuthExceptionEntryPoint extends OAuth2AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        RestResponse restResponse = new RestResponse(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
        HttpUtil.writerError(restResponse,response);
    }

}
