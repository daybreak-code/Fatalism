package cn.daycode.fatalism.repayment.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class EqualPrincipalRepayment {

    private Map<Integer, BigDecimal> amountMap;

    private Map<Integer, BigDecimal> interestMap;

    private BigDecimal principal;

    private BigDecimal totalAmount;

    private BigDecimal totalInterest;

    private Map<Integer, BigDecimal> commissionMap;
}
