package cn.daycode.fatalism.api.depository.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoanDetailRequest {

    private BigDecimal amount;

    private String preRequestNo;
}
