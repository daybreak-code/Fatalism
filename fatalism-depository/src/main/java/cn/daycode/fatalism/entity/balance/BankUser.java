package cn.daycode.fatalism.entity.balance;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("bank_user")
public class BankUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @TableField("FULLNAME")
    private String fullName;

    @TableField("ID_NUMBER")
    private String idNumber;

    @TableField("MOBILE")
    private String mobile;

    @TableField("USER_TYPE")
    private Integer userType;
}
