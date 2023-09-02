package cn.daycode.fatalism.api.depository.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "UserAutoPreTransactionRequest")
public class UserAutoPreTransactionRequest {

    @ApiModelProperty("amount")
    private BigDecimal amount;

    @ApiModelProperty("requestNo")
    private String requestNo;

    @ApiModelProperty("userNo (invester)")
    private String userNo;

    @ApiModelProperty("pre handle userNo")
    private String bizType;

    @ApiModelProperty("preMarketingAmount")
    private BigDecimal preMarketingAmount;

    @ApiModelProperty("remark")
    private String remark;

    @ApiModelProperty("projectNo")
    private String projectNo;

    @ApiModelProperty("Credit transfer serial number, this parameter needs to be filled in when purchasing credit")
    private String credisaleRequestNo;

    @ApiModelProperty("pre-handle business ID")
    private Long id;
}
