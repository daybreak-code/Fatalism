package cn.daycode.fatalism.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;


@Data
@ApiModel(value = "ConfirmLoanResponse", description = "放款确认返回信息")
public class ConfirmLoanResponse extends BaseResponse {

}
