package cn.daycode.fatalism.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @TableField("USER_NO")
    private String userNO;

    @TableField("FULLNAME")
    private String fullName;

    @TableField("ID_NUMBER")
    private String idNumber;

    @TableField("PASSWORD")
    private String password;

    @TableField("MOBILE")
    private String mobile;

    @TableField("USER_TYPE")
    private Integer userType;

    @TableField("ROLE")
    private String role;

    @TableField("AUTH_LIST")
    private String authList;

    @TableField("IS_BIND_CARD")
    private Integer isBindCard;

    @TableField("APP_CODE")
    private String appCode;

    @TableField("REQUEST_NO")
    private String requestNo;

    @TableField(value = "CREATE_DATE", fill = FieldFill.INSERT)
    private LocalDateTime createDate;
}
