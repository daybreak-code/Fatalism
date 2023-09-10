package cn.daycode.fatalism.common.domain;

public enum RemoteReturnCode implements ReturnCode{

    SUCCESS("00000", "SUCCESS"),
    EXCEPTION("00001", "EXCEPTION"),
    INTERN_ERROR("00002", "INTERN_ERROR"),
    PARAM_FAIL("00003", "PARAM_FAIL"),
    SIGN_FAIL("00004", "SIGN_FAIL"),

    BANK_CARD_BALANCE_NOT_ENOUGH("30041", "BANK_CARD_BALANCE_NOT_ENOUGH"),
    BALANCE_NOT_ENOUGH("30042", "BALANCE_NOT_ENOUGH"),
    TENDER_NOT_EXIST("30010", "TENDER_NOT_EXIST"),

    CUSTOM("99998","CUSTOM"),
    UNKNOWN("99999","UNKNOWN");
    ;

    private String code;
    private String desc;

    RemoteReturnCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static RemoteReturnCode setErrorCode(String code) {
        for (RemoteReturnCode errorCode : RemoteReturnCode.values()) {
            if (errorCode.getCode() == code) {
                return errorCode;
            }
        }
        return null;
    }
}
