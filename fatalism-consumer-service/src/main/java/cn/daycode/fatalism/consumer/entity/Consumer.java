package cn.daycode.fatalism.consumer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@TableName("consumer")
public class Consumer implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @TableField("USERNAME")
    private String username;

    @TableField("FULLNAME")
    private String fullName;

    @TableField("ID_NUMBER")
    private String idNumber;

    @TableField("USER_NO")
    private String userNo;

    @TableField("MOBILE")
    private String mobile;

    @TableField("USER_TYPE")
    private String userType;

    @TableField("ROLE")
    private String role;

    @TableField("AUTH_LIST")
    private String authList;

    @TableField("IS_BIND_CARD")
    private Integer isBindCard;

    @TableField("STATUS")
    private Integer status;

    @TableField("LOAD_AMOUNT")
    private BigDecimal loadAmount;

    @TableField("REQUEST_NO")
    private String requestNo;

}
