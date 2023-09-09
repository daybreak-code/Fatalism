package cn.daycode.fatalism.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
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
public class Tender implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private Long id;

    @TableField("USER_NO")
    private String userNo;

    @TableField("PROJECT_NO")
    private String projectNo;

    @TableField("AMOUNT")
    private BigDecimal amount;

    @TableField("TENDER_STATUS")
    private String tenderStatus;

    @TableField(value = "CREATE_DATE",  fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    @TableField(value = "MODIFY_DATE", fill = FieldFill.UPDATE)
    private LocalDateTime modifyDate;


    @TableField("REQUEST_NO")
    private String requestNo;


    @TableField("REMARK")
    private String remark;
}
