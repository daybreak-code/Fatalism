package cn.daycode.fatalism.transaction.common.constant;

public enum TradingCode {
    FROZEN("FROZEN", "Have Freezen"),

    LOAN("LOAN", "Has Loan"),

    REFUNDED("REFUNDED", "Has Refund");


    private String code;
    private String desc;

    TradingCode(String code, String desc) {
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
