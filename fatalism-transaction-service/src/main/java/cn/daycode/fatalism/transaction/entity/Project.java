package cn.daycode.fatalism.transaction.entity;

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
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private Long id;

    @TableField("CONSUMER_ID")
    private Long consumerId;

    @TableField("USER_NO")
    private String userNo;

    @TableField("PROJECT_NO")
    private String projectNo;

    @TableField("NAME")
    private String name;

    @TableField("DESCRIPTION")
    private String description;

    @TableField("TYPE")
    private String type;

    @TableField("PERIOD")
    private Integer period;

    @TableField("ANNUAL_RATE")
    private BigDecimal annualRate;

    @TableField("BORROWER_ANNUAL_RATE")
    private BigDecimal borrowerAnnualRate;

    @TableField("COMMISSION_ANNUAL_RATE")
    private BigDecimal commissionAnnualRate;

    @TableField("REPAYMENT_WAY")
    private String repaymentWay;

    @TableField("AMOUNT")
    private BigDecimal amount;

    @TableField("PROJECT_STATUS")
    private String projectStatus;

    @TableField("CREATE_DATE")
    private LocalDateTime createDate;

    @TableField("STATUS")
    private Integer status;

    @TableField("IS_ASSIGNMENT")
    private Integer isAssignment;

    @TableField("REQUEST_NO")
    private String requestNO;


}
