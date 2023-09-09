package cn.daycode.fatalism.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("depository_bank_card")
public class DepositoryBankCard implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @TableField("USER_ID")
    private Long userId;

    @TableField("BANK_CODE")
    private String bankCode;

    @TableField("BANK_NAME")
    private String bankName;

    @TableField("CARD_NUMBER")
    private String cardNumber;

    @TableField("MOBILE")
    private String mobile;

    @TableField("APP_CODE")
    private String appCode;

    @TableField("REQUEST_NO")
    private String requestNo;

}
