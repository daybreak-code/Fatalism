package cn.daycode.fatalism.api.depository.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DepositoryBaseResponse {

    @ApiModelProperty("return code")
    private String respCode;

    @ApiModelProperty("description message")
    private String respMsg;

    @ApiModelProperty("transaction status")
    private Integer status;

    @ApiModelProperty("request serial number")
    private String requestNo;

}
