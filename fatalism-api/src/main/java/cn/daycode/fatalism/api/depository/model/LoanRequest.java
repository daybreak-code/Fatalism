package cn.daycode.fatalism.api.depository.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class LoanRequest {

    private List<LoanDetailRequest> details;

    private BigDecimal commission;

    private String projectNo;

    private String requestNo;

    private Long id;

}
