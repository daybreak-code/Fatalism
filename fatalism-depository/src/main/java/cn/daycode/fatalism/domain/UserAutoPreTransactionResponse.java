package cn.daycode.fatalism.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(value = "UserAutoPreTransactionResponse", description = "预授权处理返回信息")
public class UserAutoPreTransactionResponse extends BaseResponse {

	@ApiModelProperty("预处理业务类型")
	private String bizType;
}
