package cn.daycode.fatalism.entity.balance;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@TableName("balance_details")
public class BalanceDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @TableField("USER_NO")
    private String userNo;

    @TableField("CHANGE_TYPE")
    private Integer changeType;

    @TableField("AMOUNT")
    private BigDecimal amount;

    @TableField("FREEZE_AMOUNT")
    private BigDecimal freezeAmount;

    @TableField("BALANCE")
    private BigDecimal balance;

    @TableField("APP_CODE")
    private String appCode;

    @TableField(value = "CREATE_DATE", fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    @TableField("REQUEST_CONTENT")
    private String requestContent;
}
