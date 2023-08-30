package cn.daycode.fatalism.common.domain;

public enum DepositoryErrorCode{

    RETURN_CODE_00000("00000", "Success"),

    RETURN_CODE_00001("00001", "System Exception"),

    RETURN_CODE_00002("00002", "System Internal Error"),

    RETURN_CODE_00003("00003", "Argument Validation Error"),

    RETURN_CODE_00004("00004", "Signature Verify Error"),
    ;

    DepositoryErrorCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
