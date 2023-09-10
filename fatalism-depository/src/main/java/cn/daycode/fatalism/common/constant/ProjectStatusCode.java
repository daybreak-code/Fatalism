package cn.daycode.fatalism.common.constant;

public enum ProjectStatusCode {

    RAISE("COLLECTING", "COLLECTING"),


    FULLY("FULLY", "FULLY"),


    REPAYMENT("REPAYING", "REPAYING"),


    TRUNCATE("FINISH", "FINISH"),


    FAILED_AUCTION("MISCARRY", "MISCARRY");

    private String code;
    private String desc;

    ProjectStatusCode(String code, String desc) {
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
