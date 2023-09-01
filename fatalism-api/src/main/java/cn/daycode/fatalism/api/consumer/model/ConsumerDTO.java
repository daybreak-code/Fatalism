package cn.daycode.fatalism.api.consumer.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "ConsumerDTO", description = "platform c side user information")
public class ConsumerDTO {

    @ApiModelProperty("user id")
    private Long id;

    @ApiModelProperty("user name")
    private String userName;

    @ApiModelProperty("full name")
    private String fullName;

    @ApiModelProperty("id number")
    private String idNumber;

    @ApiModelProperty("user encode")
    private String userNo;

    @ApiModelProperty("mobile")
    private String mobile;

    @ApiModelProperty("user type")
    private String userType;

    @ApiModelProperty("role")
    private String role;

    @ApiModelProperty("depository auth list")
    private String authList;

    @ApiModelProperty("if bind card")
    private Integer isBindCard;

    @ApiModelProperty("enable status")
    private Integer status;

    @ApiModelProperty("amount of loan")
    private BigDecimal loanAmount;

}
