package cn.daycode.fatalism.common.domain;

public enum LocalReturnCode implements ReturnCode {

    SUCCESS("00000", "SUCCESS"),
    EXCEPTION("00001", "EXCEPTION"),
    E_200101("60101","Bank Card Exist"),
    E_200102("60102","Bank Card Not Exist"),
    E_200103("60103","Bank Card Message Error"),

    E_200201("600201","User Encode Error"),


    E_200301("60301","Personal Info Verify Error"),
    E_200302("60302","User Transaction Error"),
            ;

    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }


    LocalReturnCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static LocalReturnCode setErrorCode(String code) {
        for (LocalReturnCode errorCode : LocalReturnCode.values()) {
            if (errorCode.getCode() == code) {
                return errorCode;
            }
        }
        return null;
    }
}
