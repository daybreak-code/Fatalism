package cn.daycode.fatalism.api.account.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "AccountDTO", description = "account information")
public class AccountDTO {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("username")
    private String username;

    @ApiModelProperty("phone")
    private String mobile;

    @ApiModelProperty("account status")
    private Integer status;

    @ApiModelProperty("domain: [c: c's user, b: b's user]")
    private String domain;
}
