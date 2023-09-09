package cn.daycode.fatalism.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("withdraw_details")
public class WithdrawDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @TableField("REQUEST_NO")
    private String requestNo;


    @TableField("USER_NO")
    private String userNo;


    @TableField("AMOUNT")
    private BigDecimal amount;


    @TableField("COMMISSION")
    private BigDecimal commission;


    @TableField("CREATE_DATE")
    private LocalDateTime createDate;


    @TableField("STATUS")
    private Integer status;

    @TableField("FINISH_DATE")
    private LocalDateTime finishDate;


    @TableField("APP_CODE")
    private String appCode;

}
