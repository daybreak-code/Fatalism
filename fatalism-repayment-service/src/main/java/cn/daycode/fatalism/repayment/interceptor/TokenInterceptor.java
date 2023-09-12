package cn.daycode.fatalism.repayment.interceptor;

import cn.daycode.fatalism.api.account.model.LoginUser;
import cn.daycode.fatalism.common.util.EncryptUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

public class TokenInterceptor implements HandlerInterceptor {

    @Override
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
