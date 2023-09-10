package cn.daycode.fatalism.common.constant;

public enum StatusCode {

    STATUS_FAIL(2, "Published/Sync Publish Failure"),
    /**
     * 已发/同布
     */
    STATUS_IN(1, "Published/Sync Publish"),
    /**
     * 未发/同布
     */
    STATUS_OUT(0,"Not Publish/Sync Publish");

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
