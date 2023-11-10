package cn.daycode.fatalism.interceptor;

import cn.daycode.fatalism.common.domain.RemoteReturnCode;
import cn.daycode.fatalism.domain.BaseResponse;
import cn.daycode.fatalism.domain.DepositoryResponse;
import cn.daycode.fatalism.common.util.EncryptUtil;
import cn.daycode.fatalism.common.util.RSAUtil;
import cn.daycode.fatalism.common.util.ResponseUtil;
import cn.daycode.fatalism.service.ConfigService;
import com.alibaba.fastjson.JSON;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class VerificationInterceptor implements HandlerInterceptor {

    @Autowired
    private ConfigService configService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String serviceName = request.getParameter("serviceName");
        String platformNo = request.getParameter("platformNo");
        String signature = request.getParameter("signature");
        String reqData = request.getParameter("reqData");

        BaseResponse baseResponse = new BaseResponse();

        if (StringUtils.isEmpty(serviceName) || StringUtils.isEmpty(platformNo)
        || org.apache.commons.lang3.StringUtils.isBlank(reqData) || org.apache.commons.lang3.StringUtils.isBlank(signature)){
            baseResponse.setResp(RemoteReturnCode.PARAM_FAIL);
            //@todo
            ResponseUtil.responseOut((javax.servlet.http.HttpServletResponse) response, JSON.toJSONString(new DepositoryResponse<>(baseResponse)));
            return false;
        }

        try {
            JSON.parse(EncryptUtil.decodeUTF8StringBase64(reqData));
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            baseResponse.setResp(RemoteReturnCode.PARAM_FAIL);
            //@todo
            ResponseUtil.responseOut((javax.servlet.http.HttpServletResponse) response, JSON.toJSONString(new DepositoryResponse<>(baseResponse)));
            return false;
        }

        return true;
    }

    private boolean verifySignature(String platformNo, String signature, String reqData){
        String p2pPublicKey = configService.getP2PPublicKey(platformNo);
        if (org.apache.commons.lang.StringUtils.isBlank(p2pPublicKey)) {
            return false;
        }
        return RSAUtil.verify(reqData, signature, p2pPublicKey, "utf-8");
    }

}
