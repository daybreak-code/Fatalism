package cn.daycode.fatalism.depository.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DepositoryRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private Long id;


    @TableField("REQUEST_NO")
    private String requestNo;

    @TableField("REQUEST_TYPE")
    private String requestType;


    @TableField("OBJECT_TYPE")
    private String objectType;


    @TableField("OBJECT_ID")
    private Long objectId;


    @TableField("CREATE_DATE")
    private LocalDateTime createDate;


    @TableField("IS_SYN")
    private Integer isSyn;


    @TableField("REQUEST_STATUS")
    private Integer requestStatus;


    @TableField("CONFIRM_DATE")
    private LocalDateTime confirmDate;


    @TableField("RESPONSE_DATA")
    private String responseData;

    public DepositoryRecord() {
    }

    public DepositoryRecord(String requestNo, String requestType, String objectType, Long objectId) {
        this.requestNo = requestNo;
        this.requestType = requestType;
        this.objectType = objectType;
        this.objectId = objectId;
    }
}
