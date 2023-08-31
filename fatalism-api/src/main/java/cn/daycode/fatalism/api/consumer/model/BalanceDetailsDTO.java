package cn.daycode.fatalism.api.consumer.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel(value = "BalanceDetailsDTO", description = "User Balance Information")
public class BalanceDetailsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "user ID")
    private Long consumerId;

    @ApiModelProperty(value = "user encode, generate single one user into depository ID")
    private String userNo;

    @ApiModelProperty(value = "account change type: 1. add, 2. reduce, 3. freeze, 4. unfreeze")
    private Integer changeType;

    @ApiModelProperty(value = "freeze Amount")
    private BigDecimal freezeAmount;

    @ApiModelProperty(value = "can use balance")
    private BigDecimal balance;


    @ApiModelProperty(value = "APP encode")
    private String appCode;

    @ApiModelProperty(value = "account change time")
    private LocalDateTime createDate;
}
