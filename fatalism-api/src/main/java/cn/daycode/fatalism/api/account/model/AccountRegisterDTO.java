package cn.daycode.fatalism.api.account.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "AccountRegisterDTO", description = "Account Register information")
public class AccountRegisterDTO {

    @ApiModelProperty("username")
    private String username;

    @ApiModelProperty("mobile")
    private String mobile;

    @ApiModelProperty("password")
    private String password;
}
