package cn.daycode.fatalism.transaction.common.utils;

import java.math.BigDecimal;

public class IncomeCalcUtil {

    public static BigDecimal getIncomeTotalInterest(BigDecimal invest, BigDecimal yearRate, int month) {
        double monthRate = yearRate.doubleValue() / 12;
        BigDecimal totalInterest = new BigDecimal(0);
        for (int i = 1; i < month + 1; i++) {
            BigDecimal multiply = invest.multiply(new BigDecimal(monthRate));
            BigDecimal sub = new BigDecimal(Math.pow(1 + monthRate, month)).subtract(new BigDecimal(Math.pow(1 + monthRate, i - 1)));
            BigDecimal monthInterest = multiply.multiply(sub).divide(new BigDecimal(Math.pow(1 + monthRate, month) - 1), 6, BigDecimal.ROUND_DOWN);
            monthInterest = monthInterest.setScale(2, BigDecimal.ROUND_DOWN);
            totalInterest = totalInterest.add(monthInterest);
        }
        return totalInterest;
    }


    public static void main(String[] args) {
        BigDecimal invest = new BigDecimal(10000);
        int month = 12;
        BigDecimal yearRate = new BigDecimal(0.1);
        System.out.println(getIncomeTotalInterest(invest, yearRate, month));
    }
}
