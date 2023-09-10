package cn.daycode.fatalism.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;


@Data
@ApiModel(value = "ConfirmLoanDetailRequest", description = "放款明细信息")
public class ConfirmLoanDetailRequest {

    @ApiModelProperty("放款金额")
    private BigDecimal amount;


	@ApiModelProperty("预处理业务流水号")
	private String preRequestNo;
}
