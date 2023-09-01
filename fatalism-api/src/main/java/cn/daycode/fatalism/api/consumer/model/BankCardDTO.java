package cn.daycode.fatalism.api.consumer.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "BankCardDTO", description = "bank card information")
public class BankCardDTO {

    @ApiModelProperty("identity")
    private Long id;

    @ApiModelProperty("consumer Id")
    private Long consumerId;

    @ApiModelProperty("full name")
    private String fullName;

    @ApiModelProperty("bank code")
    private String bankCode;

    @ApiModelProperty("bank name")
    private String bankName;

    @ApiModelProperty("card number")
    private String cardNumber;

    @ApiModelProperty("mobile")
    private String mobile;

    @ApiModelProperty("status")
    private Integer status;
}
