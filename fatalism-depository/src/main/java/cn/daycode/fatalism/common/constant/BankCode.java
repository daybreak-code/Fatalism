package cn.daycode.fatalism.common.constant;

public enum BankCode {

    ICBC("ICBC", "Industrial and Commercial Bank of China"),
    CCB("CCB", "China Construction Bank"),
    ABC("ABC", " Agricultural Bank of China"),
    BOC("BOC", "China Bank"),
    BCM("BCM", "Bank of Communications"),
    CMB("CMB", "China Merchants Bank"),
    CMBC("CMBC", "China Minsheng Bank"),
    CIB("CIB", "Industrial Bank");


    private String code;
    private String desc;

    BankCode(String code, String desc) {
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
