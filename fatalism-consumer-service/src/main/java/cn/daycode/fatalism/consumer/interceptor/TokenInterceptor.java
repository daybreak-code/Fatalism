package cn.daycode.fatalism.consumer.interceptor;

import cn.daycode.fatalism.api.account.model.LoginUser;
import cn.daycode.fatalism.common.util.EncryptUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

public class TokenInterceptor implements HandlerInterceptor {

    @Override  //前置拦截器
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) {
        String jsonToken = httpServletRequest.getParameter("jsonToken");
        if (StringUtils.isNotBlank(jsonToken)) {
            LoginUser loginUser = JSON
                    .parseObject(EncryptUtil.decodeUTF8StringBase64(jsonToken), new TypeReference<LoginUser>() {
                    });
            httpServletRequest.setAttribute("jsonToken", loginUser);
        }
        return true;
    }

}
