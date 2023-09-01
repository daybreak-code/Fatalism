package cn.daycode.fatalism.api.depository.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "DepositoryRecordDTO", description = "depository transaction record table")
public class DepositoryRecordDTO {

    @ApiModelProperty(value = "primary key")
    private Long id;

    @ApiModelProperty(value = "request serial number")
    private String requestNo;

    @ApiModelProperty(value = "request type: 1. user information(add, edit), 2. Binding information")
    private String requestType;

    @ApiModelProperty(value = "business entity type")
    private String objectType;

    @ApiModelProperty(value = "related entity id")
    private Long objectId;

    @ApiModelProperty(value = "createDate")
    private LocalDateTime createDate;

    @ApiModelProperty(value = "isSyn")
    private Integer isSyn;

    @ApiModelProperty(value = "requestStatus")
    private Integer requestStatus;

    @ApiModelProperty(value = "confirmDate")
    private LocalDateTime confirmDate;

    @ApiModelProperty(value = "responseData")
    private String responseData;

}
