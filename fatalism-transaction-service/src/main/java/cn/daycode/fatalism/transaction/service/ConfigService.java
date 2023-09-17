package cn.daycode.fatalism.transaction.service;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@EnableApolloConfig
public class ConfigService {

    @ApolloConfig
    private Config config;


    public Integer getMiscarryDays() {
        return Integer.parseInt(config.getProperty("miscarry.days", "15"));
    }


    public BigDecimal getCommissionBorrowerAnnualRate() {
        return new BigDecimal(config.getProperty("commission.borrower.annual.rate", null));
    }


    public BigDecimal getCommissionInvestorAnnualRate() {
        return new BigDecimal(config.getProperty("commission.investor.annual.rate", null));
    }



    public BigDecimal getCommissionAnnualRate() {
        return getCommissionBorrowerAnnualRate().add(getCommissionInvestorAnnualRate());
    }


    public BigDecimal getBorrowerAnnualRate() {
        return new BigDecimal(config.getProperty("borrower.annual.rate", null));
    }


    public BigDecimal getAnnualRate() {
        return getBorrowerAnnualRate().subtract(getCommissionAnnualRate());
    }


    public BigDecimal getMiniInvestmentAmount() {
        return new BigDecimal(config.getProperty("mini.investment.amount", "100.0"));
    }
}
