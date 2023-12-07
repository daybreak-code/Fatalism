package cn.daycode.fatalism.api.transaction.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProjectDTO {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private Long consumerId;

    private String userNo;

    private String projectNo;

    private String name;

    private String description;

    private String type;

    private Integer period;

    private BigDecimal borrowAnnualRate;

    private BigDecimal commissionAnnualRate;

    private String repaymentWay;

    private BigDecimal amount;

    private BigDecimal annualRate;

    private String projectStatus;

    private LocalDateTime createDate;

    private Integer status;

    private Integer isAssignment;

    private String requestNo;

    private BigDecimal remainingAmount;

    private String risk = "B";

    private Long tenderCount;
}
