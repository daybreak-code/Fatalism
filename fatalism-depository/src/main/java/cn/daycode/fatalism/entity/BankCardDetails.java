package cn.daycode.fatalism.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@TableName("bank_card_details")
public class BankCardDetails implements Serializable {

    private static final long serialVersionUID = 2L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @TableField("BANK_CARD_ID")
    private Long bankCardId;

    @TableField("CHANGE_TYPE")
    private Integer changeType;

    @TableField("MONEY")
    private BigDecimal money;

    @TableField("BALANCE")
    private BigDecimal balance;

    @TableField(value = "CREATE_DATE", fill = FieldFill.INSERT)
    private LocalDateTime createDate;

}
