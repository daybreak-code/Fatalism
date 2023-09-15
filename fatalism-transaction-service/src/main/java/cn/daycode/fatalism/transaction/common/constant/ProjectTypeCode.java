package cn.daycode.fatalism.transaction.common.constant;

public enum ProjectTypeCode {

    TYPE_CODE_NEW("NEW", "New Added Tender"),

    TYPE_CODE_STOCK("STOCK", "Existed Tender");

    private String code;
    private String desc;

    ProjectTypeCode(String code, String desc) {
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
