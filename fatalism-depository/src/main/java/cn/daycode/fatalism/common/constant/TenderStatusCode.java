package cn.daycode.fatalism.common.constant;

public enum TenderStatusCode {

    FROZEN("FROZEN", "FROZEN"),

    LOAN("LOAN", "LOAN"),

    REFUNDED("REFUNDED", "REFUNDED");


    private String code;
    private String desc;

    TenderStatusCode(String code, String desc) {
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
