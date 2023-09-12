package cn.daycode.fatalism.repayment.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ReceivablePlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private Long id;

    @TableField("CONSUMER_ID")
    private Long consumerId;

    @TableField("USER_NO")
    private String userNo;

    @TableField("TENDER_ID")
    private Long tenderId;

    @TableField("REPAYMENT_ID")
    private Long repaymentId;


    @TableField("NUMBER_OF_PERIODS")
    private Integer numberOfPeriods;


    @TableField("INTEREST")
    private BigDecimal interest;


    @TableField("PRINCIPAL")
    private BigDecimal principal;


    @TableField("AMOUNT")
    private BigDecimal amount;


    @TableField("SHOULD_RECEIVABLE_DATE")
    private LocalDateTime shouldReceivableDate;


    @TableField("RECEIVABLE_STATUS")
    private Integer receivableStatus;

    @TableField("CREATE_DATE")
    private LocalDateTime createDate;

    @TableField("COMMISSION")
    private BigDecimal commission;

}
