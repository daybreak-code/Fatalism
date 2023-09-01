package cn.daycode.fatalism.api.depository;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "GatewayRequest", description = "Signature request data for interfacing with the bank depository system")
public class GatewayRequest {

    @ApiModelProperty("Depository API Name by Request")
    private String serviceName;

    @ApiModelProperty("Platform Number")
    private String platformNo;

    @ApiModelProperty("Business data packet, json format")
    private String reqData;

    @ApiModelProperty("Signature for request data param")
    private String signature;

    @ApiModelProperty("Bank Depository System Address")
    private String depositoryUrl;
}
