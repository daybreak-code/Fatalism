package cn.daycode.fatalism.account.service;

import cn.daycode.fatalism.account.common.AccountErrorCode;
import cn.daycode.fatalism.common.domain.BusinessException;
import cn.daycode.fatalism.common.domain.CommonErrorCode;
import cn.daycode.fatalism.common.domain.RestResponse;
import cn.daycode.fatalism.common.util.OkHttpUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    @Value("${sms.url}")
    private String smsURL;

    @Value("${sms.enable}")
    private Boolean smsEnable;

    public RestResponse getSMSCode(String mobile){
        if (smsEnable){
            return OkHttpUtil.post(smsURL + "/generate?effectiveTime=300&name=sms", "{\"mobile\":" + mobile + "}");
        }
        return RestResponse.success();
    }

    public void verifySmsCode(String key, String code){
        if (smsEnable){
            StringBuilder params = new StringBuilder("/verify?name=sms");
            params.append("&verificationKey=").append(key).append("&verificationCode=").append(code);
            RestResponse smsResponse = OkHttpUtil.post(smsURL + params, "");
            if (smsResponse.getCode() != CommonErrorCode.SUCCESS.getCode() || smsResponse.getResult().toString().equalsIgnoreCase("false")) {
                throw new BusinessException(AccountErrorCode.E_140152);
            }
        }
    }

}
