//package cn.daycode.fatalism.gateway.config;
//
//import cn.daycode.fatalism.gateway.util.HttpUtil;
//import cn.daycode.fatalism.common.domain.RestResponse;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//public class RestOAuth2AuthExceptionEntryPoint extends OAuth2AuthenticationEntryPoint {
//
//
//    public RestOAuth2AuthExceptionEntryPoint() {
//        super();
//    }
//
//    @Override
//    public void setRealmName(String realmName) {
//        super.setRealmName(realmName);
//    }
//
//    @Override
//    public void setTypeName(String typeName) {
//        super.setTypeName(typeName);
//    }
//
//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//        super.commence(request, response, authException);
//    }
//
//    @Override
//    protected ResponseEntity<?> enhanceResponse(ResponseEntity<?> response, Exception exception) {
//        return super.enhanceResponse(response, exception);
//    }
//
//    @Override
//    public void commence(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, AuthenticationException authException) throws IOException, jakarta.servlet.ServletException {
//
//    }
//}
