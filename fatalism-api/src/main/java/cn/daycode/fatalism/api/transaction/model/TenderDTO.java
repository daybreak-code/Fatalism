package cn.daycode.fatalism.api.transaction.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TenderDTO {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private Long consumerId;

    private String consumerUsername;

    private String userNo;

    private Long projectId;

    private String projectNo;

    private BigDecimal amount;

    private String tenderStatus;

    private LocalDateTime createDate;

    private String requestNo;

    private Integer status;

    private String projectName;

    private Integer projectPeriod;

    private BigDecimal projectAnnualRate;

    private ProjectDTO project;

    private BigDecimal expectedIncome;

}
