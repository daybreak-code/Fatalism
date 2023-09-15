package cn.daycode.fatalism.transaction.common.constant;


public enum TradingStatusCode{

    STATUS_ACCEPT("ACCEPT", "已受理"),

    STATUS_SUCCESS("SUCCESS", "交易成功"),

    STATUS_FAIL("FAIL", "交易失败"),

    STATUS_ONSALE("ONSALE", "债权出让中"),

    STATUS_COMPLETED("COMPLETED", "已结束"),

    STATUS_INIT("INIT", "初始状态"),

    STATUS_PROCESSING("PROCESSING", "处理中"),

    STATUS_REFUNDED("REFUNDED", "已退款");


    private String code;
    private String desc;

    TradingStatusCode(String code, String desc) {
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
