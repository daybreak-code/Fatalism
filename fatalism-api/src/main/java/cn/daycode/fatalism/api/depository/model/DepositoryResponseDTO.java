package cn.daycode.fatalism.api.depository.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class DepositoryResponseDTO<T> implements Serializable {

    @ApiModelProperty("business data packet, json format")
    private T respData;

    @ApiModelProperty("signature data")
    private String signature;

}
