package cn.daycode.fatalism.repayment.util;

import cn.daycode.fatalism.repayment.model.EqualInterestRepayment;
import cn.daycode.fatalism.repayment.model.EqualPrincipalRepayment;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class RepaymentUtil {

    public static EqualInterestRepayment fixedRepayment(BigDecimal invest, BigDecimal yearRate, int month, BigDecimal commission) {
        double monthRate = yearRate.doubleValue() / 12;
        EqualInterestRepayment equalInterestRepayment = new EqualInterestRepayment();
        equalInterestRepayment.setAmount(getRepaymentAmount(invest, monthRate, month));
        equalInterestRepayment.setInterestMap(getRepaymentList(invest, monthRate, month));
        equalInterestRepayment.setPrincipalMap(getRepaymentPrincipalList(invest, monthRate, month));
        equalInterestRepayment.setTotalAmount(getRepaymentTotalAmount(invest, monthRate, month));
        equalInterestRepayment.setTotalInterest(getRepaymentTotalInterest(invest, monthRate, month));
        double comMonthRateRate = commission.doubleValue() / 12;
        equalInterestRepayment.setCommissionMap(getRepaymentList(invest, comMonthRateRate, month));
        return equalInterestRepayment;
    }


    private static BigDecimal getRepaymentAmount(BigDecimal invest, Double monthRate, int month) {
        return invest.multiply(new BigDecimal(monthRate * Math.pow(1 + monthRate, month)))
                .divide(new BigDecimal(Math.pow(1 + monthRate, month) - 1), 2, BigDecimal.ROUND_DOWN);
    }


    private static Map<Integer, BigDecimal> getRepaymentList(BigDecimal invest, Double monthRate, int month) {
        Map<Integer, BigDecimal> map = new HashMap<Integer, BigDecimal>();
        BigDecimal monthInterest;
        for (int i = 1; i < month + 1; i++) {
            BigDecimal multiply = invest.multiply(new BigDecimal(monthRate));
            BigDecimal sub = new BigDecimal(Math.pow(1 + monthRate, month)).subtract(new BigDecimal(Math.pow(1 + monthRate, i - 1)));
            monthInterest = multiply.multiply(sub).divide(new BigDecimal(Math.pow(1 + monthRate, month) - 1), 6, BigDecimal.ROUND_DOWN);
            monthInterest = monthInterest.setScale(2, BigDecimal.ROUND_DOWN);
            map.put(i, monthInterest);
        }
        return map;
    }


    private static Map<Integer, BigDecimal> getRepaymentPrincipalList(BigDecimal invest, Double monthRate, int month) {
        BigDecimal monthIncome = invest.multiply(new BigDecimal(monthRate * Math.pow(1 + monthRate, month)))
                .divide(new BigDecimal(Math.pow(1 + monthRate, month) - 1), 2, BigDecimal.ROUND_DOWN);
        Map<Integer, BigDecimal> mapInterest = getRepaymentList(invest, monthRate, month);
        Map<Integer, BigDecimal> mapPrincipal = new HashMap<Integer, BigDecimal>();
        for (Map.Entry<Integer, BigDecimal> entry : mapInterest.entrySet()) {
            mapPrincipal.put(entry.getKey(), monthIncome.subtract(entry.getValue()));
        }
        return mapPrincipal;
    }


    private static BigDecimal getRepaymentTotalInterest(BigDecimal invest, Double monthRate, int month) {
        BigDecimal count = new BigDecimal(0);
        Map<Integer, BigDecimal> mapInterest = getRepaymentList(invest, monthRate, month);
        for (Map.Entry<Integer, BigDecimal> entry : mapInterest.entrySet()) {
            count = count.add(entry.getValue());
        }
        return count;
    }


    private static BigDecimal getRepaymentTotalAmount(BigDecimal invest, double monthRate, int month) {
        BigDecimal perMonthInterest = invest.multiply(new BigDecimal(monthRate * Math.pow(1 + monthRate, month)))
                .divide(new BigDecimal(Math.pow(1 + monthRate, month) - 1), 2, BigDecimal.ROUND_DOWN);
        BigDecimal count = perMonthInterest.multiply(new BigDecimal(month));
        count = count.setScale(2, BigDecimal.ROUND_DOWN);
        return count;
    }



    public static EqualPrincipalRepayment fixedCapital(BigDecimal invest, BigDecimal yearRate, int month, BigDecimal commission) {
        double monthRate = yearRate.doubleValue() / 12;
        EqualPrincipalRepayment equalPrincipalRepayment = new EqualPrincipalRepayment();
        equalPrincipalRepayment.setAmountMap(getCapitalAmountList(invest, monthRate, month));
        equalPrincipalRepayment.setInterestMap(getCapitalInterestList(invest, monthRate, month));
        equalPrincipalRepayment.setPrincipal(getCapitalPrincipal(invest, month));
        equalPrincipalRepayment.setTotalInterest(getCapitalTotalInterest(invest, monthRate, month));
        equalPrincipalRepayment.setTotalAmount(invest.add(equalPrincipalRepayment.getTotalInterest()));
        double comMonthRateRate = commission.doubleValue() / 12;
        equalPrincipalRepayment.setCommissionMap(getCapitalInterestList(invest, comMonthRateRate, month));
        return equalPrincipalRepayment;
    }


    private static Map<Integer, BigDecimal> getCapitalAmountList(BigDecimal invest, double monthRate, int month) {
        Map<Integer, BigDecimal> map = new HashMap<Integer, BigDecimal>();
        double monthPri = getCapitalPrincipal(invest, month).doubleValue();
        monthRate = new BigDecimal(monthRate).setScale(6, BigDecimal.ROUND_DOWN).doubleValue();
        for (int i = 1; i <= month; i++) {
            BigDecimal monthRes = new BigDecimal(monthPri + (invest.doubleValue() - monthPri * (i - 1)) * monthRate);
            monthRes = monthRes.setScale(2, BigDecimal.ROUND_DOWN);
            map.put(i, monthRes);
        }
        return map;
    }


    private static BigDecimal getCapitalPrincipal(BigDecimal invest, int month) {
        BigDecimal monthIncome = invest.divide(new BigDecimal(month), 2, BigDecimal.ROUND_DOWN);
        return monthIncome;
    }


    private static Map<Integer, BigDecimal> getCapitalInterestList(BigDecimal invest, double monthRate, int month) {
        Map<Integer, BigDecimal> inMap = new HashMap<Integer, BigDecimal>();
        BigDecimal principal = getCapitalPrincipal(invest, month);
        Map<Integer, BigDecimal> map = getCapitalAmountList(invest, monthRate, month);
        for (Map.Entry<Integer, BigDecimal> entry : map.entrySet()) {
            BigDecimal interestBigDecimal = entry.getValue().subtract(principal);
            interestBigDecimal = interestBigDecimal.setScale(2, BigDecimal.ROUND_DOWN);
            inMap.put(entry.getKey(), interestBigDecimal);
        }
        return inMap;
    }


    public static BigDecimal getCapitalTotalInterest(BigDecimal invest, double monthRate, int month) {
        BigDecimal count = new BigDecimal(0);
        Map<Integer, BigDecimal> mapInterest = getCapitalInterestList(invest, monthRate, month);
        for (Map.Entry<Integer, BigDecimal> entry : mapInterest.entrySet()) {
            count = count.add(entry.getValue());
        }
        return count;
    }


    public static void main(String[] args) {
        BigDecimal invest = new BigDecimal(10000);
        int month = 12;
        BigDecimal yearRate = new BigDecimal(0.1);


        final EqualInterestRepayment equalInterestRepayment = fixedRepayment(invest, yearRate, month, new BigDecimal(0.03));
        System.out.println("Equal principal and interest: " + equalInterestRepayment);

        final EqualPrincipalRepayment equalPrincipalRepayment = fixedCapital(invest, yearRate, month, new BigDecimal(0.03));
        System.out.println("Equivalent principal: " + equalPrincipalRepayment);
    }
}
