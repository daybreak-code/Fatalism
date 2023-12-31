package cn.daycode.fatalism.consumer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("bank_card")
public class BankCard implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @TableField("CONSUMER_ID")
    private Long consumerId;

    @TableField("BANK_NAME")
    private String bankName;

    @TableField("BANK_CODE")
    private String bankCode;

    @TableField("CARD_NUMBER")
    private String cardNumber;

    @TableField("MOBILE")
    private String mobile;

    @TableField("STATUS")
    private Integer status;

}
