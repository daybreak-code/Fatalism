package cn.daycode.fatalism.api.depository.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DepositoryConsumerResponse extends DepositoryBaseResponse{

    @ApiModelProperty("bank code")
    private String bankCode;

    @ApiModelProperty("bank name")
    private String bankName;
}
