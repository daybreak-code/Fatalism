package cn.daycode.fatalism.common.domain;

public enum PreprocessBusinessTypeCode {

    TENDER("TENDER", "投标"),

    REPAYMENT("REPAYMENT", "还款"),

    CREDIT_ASSIGNMENT("CREDIT_ASSIGNMENT", "债权购买"),

    COMPENSATORY("COMPENSATORY", "代偿"),

    COMPENSATORY_REPAYMENT("COMPENSATORY_REPAYMENT", "代偿还款"),
    ;

    private String code;
    private String desc;

    PreprocessBusinessTypeCode(String code, String desc) {
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
