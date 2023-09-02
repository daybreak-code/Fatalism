package cn.daycode.fatalism.api.transaction.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "ProjectQueryDTO")
public class ProjectQueryDTO {

    private String type;

    private String name;

    private Integer startPeriod;

    private Integer endPeriod;

    private BigDecimal startAnnualRate;

    private BigDecimal endAnnualRate;

    private BigDecimal borrowerAnnualRate;

    private String repaymentWay;

    private String projectStatus;
}
