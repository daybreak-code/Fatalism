package cn.daycode.fatalism.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@TableName("request_details")
public class RequestDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @TableField("APP_CODE")
    private String appCode;

    @TableField("REQUEST_NO")
    private String requestNo;

    @TableField("SERVICE_NAME")
    private String serviceName;

    @TableField("REQUEST_DATA")
    private String requestData;

    @TableField("RESPONSE_DATA")
    private String responseData;

    @TableField("STATUS")
    private Integer status;

    @TableField(value = "CREATE_DATE", fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    @TableField(value = "FINISH_DATE", fill = FieldFill.UPDATE)
    private LocalDateTime finishDate;

}
