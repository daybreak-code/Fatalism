package cn.daycode.fatalism.api.depository.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProjectRequestDataDTO {

    private String requestNo;

    private String userNo;

    private String projectNo;

    private BigDecimal projectAmount;

    private String projectDesc;

    private String projectType;

    private Integer projectPeriod;

    private BigDecimal annualRate;

    private String repaymentWay;

    private Integer isAssignment;

}
