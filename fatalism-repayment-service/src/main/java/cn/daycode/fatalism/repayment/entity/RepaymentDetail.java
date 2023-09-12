package cn.daycode.fatalism.repayment.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("repayment_detail")
public class RepaymentDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private Long id;

    @TableField("REPAYMENT_PLAN_ID")
    private Long repaymentPlanId;

    @TableField("AMOUNT")
    private BigDecimal amount;


    @TableField("REPAYMENT_DATE")
    private LocalDateTime repaymentDate;


    @TableField("REQUEST_NO")
    private String requestNo;


    @TableField("STATUS")
    private Integer status;


}