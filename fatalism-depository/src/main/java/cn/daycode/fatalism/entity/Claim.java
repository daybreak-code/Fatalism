package cn.daycode.fatalism.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Claim implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private Long id;

    @TableField("PROJECT_ID")
    private Long projectId;

    @TableField("PROJECT_NO")
    private String projectNo;

    @TableField("CONSUMER_ID")
    private Long consumerId;

    @TableField("SOURCE_TENDER_ID")
    private Long sourceTenderId;

    @TableField("ROOT_PROJECT_ID")
    private Long rootProjectId;

    @TableField("ROOT_PROJECT_NO")
    private String rootProjectNo;

    @TableField("ASSIGNMENT_REQUEST_NO")
    private String assignmentRequestNo;

}
