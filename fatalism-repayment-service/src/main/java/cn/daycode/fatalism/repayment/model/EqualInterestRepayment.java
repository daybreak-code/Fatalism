package cn.daycode.fatalism.repayment.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class EqualInterestRepayment {

    private BigDecimal amount;

    private Map<Integer, BigDecimal> interestMap;

    private Map<Integer, BigDecimal> principalMap;

    private BigDecimal totalAmount;

    private BigDecimal totalInterest;

    private Map<Integer, BigDecimal> commissionMap;
}
