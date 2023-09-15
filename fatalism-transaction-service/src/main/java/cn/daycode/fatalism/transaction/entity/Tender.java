package cn.daycode.fatalism.transaction.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Tender implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("ID")
    private Long id;

    @TableField("CONSUMER_ID")
    private Long consumerId;

    @TableField("CONSUMER_USERNAME")
    private String consumerUserName;

    @TableField("USER_NO")
    private String userNo;

    @TableField("PROJECT_ID")
    private Long projectId;

    @TableField("PROJECT_NO")
    private String projectNo;

    @TableField("AMOUNT")
    private BigDecimal amount;

    @TableField("TENDER_STATUS")
    private String tenderStatus;

    @TableField("CREATE_DATE")
    private LocalDateTime createDate;

    @TableField("REQUEST_NO")
    private String requestNo;

    @TableField("STATUS")
    private Integer status;

    @TableField("PROJECT_NAME")
    private String projectName;

    @TableField("PROJECT_PERIOD")
    private Integer projectPeriod;

    @TableField("PROJECT_ANNUAL_RATE")
    private BigDecimal projectAnnualRate;
}
