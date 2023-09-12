package cn.daycode.fatalism.repayment.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("repayment_plan")
public class RepaymentPlan implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId("ID")
    private Long id;


    @TableField("CONSUMER_ID")
    private Long consumerId;

    @TableField("USER_NO")
    private String userNo;


    @TableField("PROJECT_ID")
    private Long projectId;


    @TableField("PROJECT_NO")
    private String projectNo;


    @TableField("NUMBER_OF_PERIODS")
    private Integer numberOfPeriods;


    @TableField("INTEREST")
    private BigDecimal interest;


    @TableField("PRINCIPAL")
    private BigDecimal principal;


    @TableField("AMOUNT")
    private BigDecimal amount;


    @TableField("SHOULD_REPAYMENT_DATE")
    private LocalDateTime shouldRepaymentDate;


    @TableField("REPAYMENT_STATUS")
    private String repaymentStatus;


    @TableField("CREATE_DATE")
    private LocalDateTime createDate;


    @TableField("COMMISSION")
    private BigDecimal commission;
}
