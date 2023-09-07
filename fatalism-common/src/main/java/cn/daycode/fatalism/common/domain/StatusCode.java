package cn.daycode.fatalism.common.domain;

public enum StatusCode {


    STATUS_FAIL(2, "publish/sync failure"),

    STATUS_IN(1, "published/synchronized"),

    STATUS_OUT(0,"don't publish/sync");

    private Integer code;
    private String desc;
    StatusCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
