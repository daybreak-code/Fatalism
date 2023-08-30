package cn.daycode.fatalism.common.domain;

public enum CodePrefixCode {

    CODE_NO_PREFIX(""),
    CODE_PROJECT_PREFIX("PRO_"),
    CODE_CONSUMER_PREFIX("USR_"),
    CODE_REQUEST_PREFIX("REQ_");

    private String code;

    CodePrefixCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
