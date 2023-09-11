package cn.daycode.fatalism.interceptor;

import cn.daycode.fatalism.common.util.EncryptUtil;
import cn.daycode.fatalism.common.util.RSAUtil;
import cn.daycode.fatalism.common.util.ResponseUtil;
import cn.daycode.fatalism.entity.RequestDetails;
import cn.daycode.fatalism.service.RequestDetailsService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class P2PRequestInterceptor implements HandlerInterceptor {

    @Value("${depository.privateKey}")
    private String depositoryPrivateKey;

    @Autowired
    private RequestDetailsService requestDetailsService;

    public boolean preHandle(HttpServletRequest httpRequest, HttpServletResponse httpResponse, Object handler)
            throws Exception {
        String serviceName = httpRequest.getParameter("serviceName");
        String platformNo = httpRequest.getParameter("platformNo");
        String reqData = EncryptUtil.decodeUTF8StringBase64(httpRequest.getParameter("reqData"));
        JSONObject reqDataJSONObj = JSON.parseObject(reqData);
        String requestNo = reqDataJSONObj.getString("requestNo");

        RequestDetails requestDetails = requestDetailsService.getByRequestNo(requestNo);
        if (requestDetails != null && httpRequest.getRequestURI().endsWith("/service")) {
            JSONObject responseJSON = new JSONObject();
            responseJSON.put("respData", JSON.parseObject(requestDetails.getResponseData()));
            responseJSON
                    .put("signature", RSAUtil.sign(requestDetails.getResponseData(), depositoryPrivateKey, "utf-8"));
            ResponseUtil.responseOut(httpResponse, responseJSON.toJSONString());
            return false;
        }

        requestDetails = new RequestDetails();
        requestDetails.setAppCode(platformNo);
        requestDetails.setServiceName(serviceName);
        requestDetails.setRequestNo(requestNo);
        requestDetails.setRequestData(reqData);
        requestDetailsService.create(requestDetails);

        return true;
    }
}
