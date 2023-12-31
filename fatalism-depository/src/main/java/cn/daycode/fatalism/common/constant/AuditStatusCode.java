package cn.daycode.fatalism.common.constant;

public enum AuditStatusCode {

    AUDIT("AUDIT", "AUDIT in Progress"),
    PASSED("PASSED", "AUDIT passed"),
    REFUSED("REFUSED", "AUDIT reject"),
    ;

    private String code;
    private String desc;

    AuditStatusCode(String code, String desc) {
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
