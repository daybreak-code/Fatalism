package cn.daycode.fatalism.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;


@Data
@ApiModel(value = "CreateProjectResponse", description = "创建标的返回数据")
public class CreateProjectResponse extends BaseResponse {
	public CreateProjectResponse(String requestNo) {
		super(requestNo);
	}
}
