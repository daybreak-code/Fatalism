package cn.daycode.fatalism.api.search.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "ProjectQueryParamsDTO")
public class ProjectQueryParamsDTO {

    private Long id;

    private Long [] ids;

    private String name;

    private String description;

    private Integer startPeriod;

    private Integer endPeriod;

    private BigDecimal startAnnualRate;

    private BigDecimal endAnnualRate;

    private String projectStatus;

    private Integer status;

    private Integer isAssignment;

}
