package cn.daycode.fatalism.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


@Data
@ApiModel(value = "ConfirmLoanRequest", description = "标的满标放款确认信息")
public class ConfirmLoanRequest extends BaseRequest {

	@ApiModelProperty("标的编码")
	private String projectNo;

	@ApiModelProperty("平台佣金")
    private BigDecimal commission;

	@ApiModelProperty("放款明细")
	private List<ConfirmLoanDetailRequest> details;

}
