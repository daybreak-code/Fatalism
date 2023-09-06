package cn.daycode.fatalism.consumer.common;

import cn.daycode.fatalism.api.account.model.LoginUser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class SecurityUtil {

    public static LoginUser getUser(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null){
            HttpServletRequest request = requestAttributes.getRequest();

            Object jwt = request.getAttribute("jsonToken");
            if (jwt instanceof LoginUser){
                return (LoginUser) jwt;
            }
        }
        return new LoginUser();
    }
}
