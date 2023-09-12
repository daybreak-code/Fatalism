package cn.daycode.fatalism.repayment.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ReceivableDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private Long id;

    @TableField("RECEIVABLE_ID")
    private Long receivableId;

    @TableField("AMOUNT")
    private BigDecimal amount;

    @TableField("RECEIVABLE_DATE")
    private LocalDateTime receivableDate;

}
