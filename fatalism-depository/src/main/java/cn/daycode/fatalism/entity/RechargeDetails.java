package cn.daycode.fatalism.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("recharge_details")
public class RechargeDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @TableField("REQUEST_NO")
    private String requestNo;

    @TableField("USER_NO")
    private String userNo;

    @TableField("AMOUNT")
    private BigDecimal amount;

    @TableField(value = "CREATE_DATE", fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    @TableField("STATUS")
    private Integer status;

    @TableField(value = "FINISH_DATE", fill = FieldFill.UPDATE)
    private LocalDateTime finishDate;

    @TableField("APP_CODE")
    private String appCode;
}
