package cn.daycode.fatalism.api.depository.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "RepaymentDetailRequest")
public class RepaymentDetailRequest {

    @ApiModelProperty("invest user num")
    private String userNo;

    @ApiModelProperty("commission from invest")
    private BigDecimal commission;

    @ApiModelProperty("dividend")
    private BigDecimal dividend;

    @ApiModelProperty("principle from invest")
    private BigDecimal amount;

    @ApiModelProperty("interest from invest")
    private BigDecimal interest;
}
