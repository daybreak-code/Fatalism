package cn.daycode.fatalism.api.transaction.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "ProjectInvestDTO")
public class ProjectInvestDTO {

    private Long id;

    private String amount;
}
