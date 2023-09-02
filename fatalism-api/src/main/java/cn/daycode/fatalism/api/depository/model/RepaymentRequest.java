package cn.daycode.fatalism.api.depository.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel(value = "RepaymentRequest")
public class RepaymentRequest {

    @ApiModelProperty("requestNo")
    private String requestNo;

    @ApiModelProperty("preRequestNo")
    private String preRequestNo;

    @ApiModelProperty("projectNo")
    private String projectNo;

    @ApiModelProperty("commission")
    private BigDecimal commission;

    @ApiModelProperty("details")
    private List<RepaymentDetailRequest> details;

    @ApiModelProperty("business id")
    private Long id;

    @ApiModelProperty("repayment amount")
    private BigDecimal amount;

}
