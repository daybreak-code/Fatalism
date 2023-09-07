package cn.daycode.fatalism.consumer.common;

import cn.daycode.fatalism.common.domain.ErrorCode;

public enum ConsumerErrorCode implements ErrorCode {

    E_140101(140101, "don't existed user information"),
    E_140102(140102, "request failed"),
    E_140105(140105, "user has open account"),
    E_140106(140106, "register failure"),
    E_140107(140107, "user has exist"),
    E_140108(140108, "identity info is not same"),
    E_140131(140131,"user recharge failure"),
    E_140132(140132,"user depository account no successful opened"),

    E_140141(140141,"user withdraw failure"),
    E_140151(140151, "bankcard has been bind");

    private int code;
    private String desc;

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    ConsumerErrorCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ConsumerErrorCode getErrorCode(int code){
        for (ConsumerErrorCode errorCode : ConsumerErrorCode.values()) {
            if (errorCode.getCode() == code){
                return errorCode;
            }
        }
        return null;
    }
}
