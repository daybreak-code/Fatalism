package cn.daycode.fatalism.repayment.sms;

import com.github.qcloudsms.SmsSingleSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class QCloudSmsServiceImpl implements SmsService{

    @Value("${sms.qcloud.appId}")
    private int appId;

    @Value("${sms.qcloud.appKey}")
    private String appKey;

    @Value("${sms.qcloud.templateId}")
    private int templateId;

    @Value("${sms.qcloud.sign}")
    private String sign;

    @Override
    public void sendRepaymentNotify(String mobile, String date, BigDecimal amount) {
        SmsSingleSender ssender = new SmsSingleSender(appId, appKey);
        try {
            ssender.sendWithParam("86", mobile,
                    templateId, new String[]{date, amount.toString()}, sign, "", "");
        }catch (Exception ex){
            log.error("Sent Error: {}",ex .getMessage());
        }
    }
}
