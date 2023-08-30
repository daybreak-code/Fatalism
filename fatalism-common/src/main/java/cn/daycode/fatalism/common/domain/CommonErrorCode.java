package cn.daycode.fatalism.common.domain;

public enum CommonErrorCode implements ErrorCode{

    SUCCESS(0, "Success"),
    FUSE(-1, "Gateway calls a fuse"),

    E_100101(100101,"The incoming parameter does not match the interface"),
    E_100102(100102,"Verification code error."),
    E_100103(100103,"Verification code is empty"),
    E_100104(100104,"The query result is empty"),
    E_100105(100105,"The ID format is incorrect or out of the Long storage range"),
    E_100106(100106,"Request Failed"),

    E_999990(999990,"Call fatalism-transaction-service Be Fused"),
    E_999991(999991,"Call fatalism-authentication-service Be Fused"),
    E_999992(999992,"Call fatalism-uua-service Be Fused"),
    E_999993(999993,"Call fatalism-resource-service Be Fused"),
    E_999994(999994,"Call fatalism-synchronization-service Be Fused"),
    E_999995(999995,"Call fatalism-account-service Be Fused"),
    E_999996(999996,"Call fatalism-depository-agent-service Be Fused"),
    E_999997(999997,"Call fatalism-repayment-service Be Fused"),

    CUSTOM(999998,"Customer Exception"),
    UNKOWN(999999,"Unknown Error");


    private int code;
    private String desc;

    CommonErrorCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }
}
