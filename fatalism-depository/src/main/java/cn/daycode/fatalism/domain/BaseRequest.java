package cn.daycode.fatalism.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class BaseRequest {

	@ApiModelProperty("请求业务流水号")
	private String requestNo;

	@ApiModelProperty(value = "应用编码")
	private String appCode;
}
