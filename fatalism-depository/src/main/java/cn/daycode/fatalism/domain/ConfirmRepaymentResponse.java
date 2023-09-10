package cn.daycode.fatalism.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;


@Data
@ApiModel(value = "ConfirmRepaymentResponse", description = "放款确认请求返回信息")
public class ConfirmRepaymentResponse extends BaseResponse {

}
