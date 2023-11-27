package cn.daycode.fatalism.account.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("account")
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private Long id;

    @TableField("USERNAME")
    private String username;

    @TableField("MOBILE")
    private String mobile;

    @TableField("PASSWORD")
    private String password;

    @TableField("SALT")
    private String salt;

    @TableField("STATUS")
    private Integer status;

    /**
     * domain(c：c side user；b：b side user)
     */
    @TableField("DOMAIN")
    private String domain;
}
