package cn.daycode.fatalism.api.consumer.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "ConsumerRequest", description = "platform c side customer create account information")
public class ConsumerRequest {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("username")
    private String userName;

    @ApiModelProperty("full name")
    private String fullName;

    @ApiModelProperty("bank code")
    private String bankCode;

    @ApiModelProperty("card number")
    private String cardNumber;

    @ApiModelProperty("phone number")
    private String mobile;

    @ApiModelProperty("role")
    private String role;

    @ApiModelProperty("request number")
    private String requestNo;

    @ApiModelProperty("user number")
    private String userNo;

    @ApiModelProperty("page callback")
    private String callbackUrl;

}
