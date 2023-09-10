package cn.daycode.fatalism.common.constant;

public enum PreTransactionCode {

    TENDER("TENDER", "TENDER"),

    REPAYMENT("REPAYMENT", "REPAYMENT"),

    CREDIT_ASSIGNMENT("CREDIT_ASSIGNMENT", "CREDIT_ASSIGNMENT"),

    COMPENSATORY("COMPENSATORY", "COMPENSATORY"),

    COMPENSATORY_REPAYMENT("COMPENSATORY_REPAYMENT", "COMPENSATORY_REPAYMENT"),
    ;

    private String code;
    private String desc;

    PreTransactionCode(String code, String desc) {
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
