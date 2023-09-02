package cn.daycode.fatalism.api.transaction.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel(value = "TenderOverviewDTO", description = "tender info display")
public class TenderOverviewDTO {

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("primary key")
    private Long id;

    @ApiModelProperty("tender user id")
    private Long consumerId;

    @ApiModelProperty("consumerUsername")
    private Long consumerUsername;

    @ApiModelProperty("tender freeze amount")
    private BigDecimal amount;

    @ApiModelProperty("tender way")
    private String tenderWay = "MANUAl_BORROW";

    @ApiModelProperty("create date")
    private LocalDateTime createDate;
}
