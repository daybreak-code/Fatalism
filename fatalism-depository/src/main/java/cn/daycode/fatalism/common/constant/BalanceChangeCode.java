package cn.daycode.fatalism.common.constant;

public enum BalanceChangeCode {

    NEW(0, "Open Account"),
    INCREASE(1, "Add"),
    DECREASE(2, "Reduce"),
    FREEZE(3, "Freeze"),
    UNFREEZE(4, "unFreeze");

    private Integer code;
    private String desc;

    BalanceChangeCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
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
