package cn.daycode.fatalism.api.consumer.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "BorrowerDTO", description = "Borrower account information")
public class BorrowerDTO {

    @ApiModelProperty("user id")
    private Long id;

    @ApiModelProperty("user name")
    private String userName;

    @ApiModelProperty("full name")
    private String fullName;

    @ApiModelProperty("id number")
    private String idNumber;

    @ApiModelProperty("mobile")
    private String mobile;

    @ApiModelProperty("age")
    private Integer age;

    @ApiModelProperty("birthday")
    private String birthday;

    @ApiModelProperty("gender")
    private String gender;
}
