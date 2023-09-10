package cn.daycode.fatalism.common.constant;

public enum TransactionStatusCode {

    ACCEPT("ACCEPT", "ACCEPT"),

    SUCCESS("SUCCESS", "SUCCESS"),

    FAIL("FAIL", "FAIL"),

    ONSALE("ONSALE", "ONSALE"),

    COMPLETED("COMPLETED", "COMPLETED"),

    INIT("INIT", "INIT"),

    PROCESSING("PROCESSING", "PROCESSING"),

    REFUNDED("REFUNDED", "REFUNDED"),
    ;

    private String code;
    private String desc;

    TransactionStatusCode(String code, String desc) {
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
