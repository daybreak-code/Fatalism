package cn.daycode.fatalism.api.consumer.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "ConsumerRegisterDTO", description = "user register info")
public class ConsumerRegisterDTO {

    @ApiModelProperty("user name")
    private String username;

    @ApiModelProperty("mobile number")
    private String mobile;

    @ApiModelProperty("password")
    private String password;

    @ApiModelProperty("user role")
    private String role;

    @ApiModelProperty("verify code / key")
    private String key;
}
