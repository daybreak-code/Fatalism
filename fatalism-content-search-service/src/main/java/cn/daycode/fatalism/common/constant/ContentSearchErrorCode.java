package cn.daycode.fatalism.common.constant;

import cn.daycode.fatalism.common.domain.ErrorCode;

public enum ContentSearchErrorCode implements ErrorCode {
    E_190102(190102,"Request Failure"),
    ;

    private int code;
    private String desc;

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    private ContentSearchErrorCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public static ContentSearchErrorCode setErrorCode(int code) {
        for (ContentSearchErrorCode errorCode : ContentSearchErrorCode.values()) {
            if (errorCode.getCode()==code) {
                return errorCode;
            }
        }
        return null;
    }
}
