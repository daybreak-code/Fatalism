package cn.daycode.fatalism.transaction.service;


import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ConfigService {


    //@todo change config pattern


    public Integer getMiscarryDays() {
        return Integer.parseInt("miscarry.days", 15);
    }


    public BigDecimal getCommissionBorrowerAnnualRate() {
        return new BigDecimal("commission.borrower.annual.rate", null);
    }


    public BigDecimal getCommissionInvestorAnnualRate() {
        return new BigDecimal("commission.investor.annual.rate", null);
    }



    public BigDecimal getCommissionAnnualRate() {
        return getCommissionBorrowerAnnualRate().add(getCommissionInvestorAnnualRate());
    }


    public BigDecimal getBorrowerAnnualRate() {
        return new BigDecimal("borrower.annual.rate", null);
    }


    public BigDecimal getAnnualRate() {
        return getBorrowerAnnualRate().subtract(getCommissionAnnualRate());
    }


    public BigDecimal getMiniInvestmentAmount() {
        return new BigDecimal("mini.investment.amount", null);
    }
}
