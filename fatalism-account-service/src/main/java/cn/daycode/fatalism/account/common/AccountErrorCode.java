package cn.daycode.fatalism.account.common;

import cn.daycode.fatalism.common.domain.ErrorCode;

public enum AccountErrorCode implements ErrorCode {
    E_130101(130101, "username has exist"),
    E_130104(130104,"user not been registered"),
    E_130105(130105, "username or password failure"),
    E_140141(140141, "register failure"),
    E_140151(140151, "got SMS verification code failure"),
    E_140152(140152,"verification code mistake")
    ;

   private int code;
   private String desc;

    AccountErrorCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    public static AccountErrorCode setErrorCode(int code){
        for (AccountErrorCode value : AccountErrorCode.values()) {
            if (value.getCode() == code){
                return value;
            }
        }
        return null;
    }
}
