package cn.daycode.fatalism.common.domain;

public enum DepositoryReturnCode {
    RETURN_CODE_00000("00000", "SUCCESS"),

    RETURN_CODE_00001("00001", "System Failure"),

    RETURN_CODE_00002("00002", "System Internal Error"),

    RETURN_CODE_00003("00003", "Param Validation Failure"),

    RETURN_CODE_00004("00004", "Signature Verify Failure"),
    ;

    private String code;
    private String desc;

    DepositoryReturnCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
