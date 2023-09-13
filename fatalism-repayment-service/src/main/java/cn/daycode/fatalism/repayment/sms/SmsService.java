package cn.daycode.fatalism.repayment.sms;

import java.math.BigDecimal;

public interface SmsService {

    void sendRepaymentNotify(String mobile, String date, BigDecimal amount);
}
