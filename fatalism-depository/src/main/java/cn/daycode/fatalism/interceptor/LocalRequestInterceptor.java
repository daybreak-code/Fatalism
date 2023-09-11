package cn.daycode.fatalism.interceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;

public class LocalRequestInterceptor implements HandlerInterceptor {

    @Value("${depository.privateKey}")
    private String depositoryPrivateKey;


}
