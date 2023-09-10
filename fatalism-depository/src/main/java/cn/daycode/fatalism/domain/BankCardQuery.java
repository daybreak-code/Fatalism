package cn.daycode.fatalism.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
@ApiModel(value = "BankCardQuery", description = "银行卡检索信息")
public class BankCardQuery implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "fullname")
	private String fullname;

	@ApiModelProperty(value = "idNumber")
	private String idNumber;

	@ApiModelProperty(value = "mobile")
	private String mobile;

	@ApiModelProperty(value = "bankCode")
	private String bankCode;

	@ApiModelProperty(value = "cardNumber")
	private String cardNumber;
}
