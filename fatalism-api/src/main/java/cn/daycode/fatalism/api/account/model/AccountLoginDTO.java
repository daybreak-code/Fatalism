package cn.daycode.fatalism.api.account.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "AccountLoginDTO", description = "Account Login Information")
public class AccountLoginDTO {

    @ApiModelProperty("username")
    private String username;

    @ApiModelProperty("phone")
    private String mobile;

    @ApiModelProperty("password")
    private String password;

    @ApiModelProperty("domain: [c: c's user, b: b's user]")
    private String domain;
}
