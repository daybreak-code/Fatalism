package cn.daycode.fatalism.depository.interceptor;

import cn.daycode.fatalism.common.domain.DepositoryErrorCode;
import cn.daycode.fatalism.common.util.EncryptUtil;
import cn.daycode.fatalism.common.util.RSAUtil;
import cn.daycode.fatalism.depository.service.ConfigService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Component
public class DepositoryNotifyVerificationInterceptor implements HandlerInterceptor {

    @Autowired
    private ConfigService configService;

    @Override
    public boolean preHandle(HttpServletRequest httpRequest, HttpServletResponse httpResponse, Object handler) {
        String serviceName = httpRequest.getParameter("serviceName");
        String platformNo = httpRequest.getParameter("platformNo");
        String signature = httpRequest.getParameter("signature");
        String reqData = httpRequest.getParameter("reqData");

        if (StringUtils.isBlank(serviceName) || StringUtils.isBlank(platformNo) || StringUtils.isBlank(reqData)
                || StringUtils.isBlank(signature)) {
            responseOut(httpResponse, DepositoryErrorCode.RETURN_CODE_00001.getDesc());
            return false;
        }

        reqData = EncryptUtil.decodeUTF8StringBase64(reqData);
        if (!verifySignature(signature, reqData)) {
            responseOut(httpResponse, DepositoryErrorCode.RETURN_CODE_00004.getDesc());
            return false;
        }

        return true;
    }


    private boolean verifySignature(String signature, String reqData) {
        String p2pPublicKey = configService.getDepositoryPublicKey();
        if (StringUtils.isBlank(p2pPublicKey)) {
            return false;
        }
        return RSAUtil.verify(reqData, signature, p2pPublicKey, "utf-8");
    }

    private void responseOut(HttpServletResponse response, String s) {
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter pw = response.getWriter()) {
            pw.write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
