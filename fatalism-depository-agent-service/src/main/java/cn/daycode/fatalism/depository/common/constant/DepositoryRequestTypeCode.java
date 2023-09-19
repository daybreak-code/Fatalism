package cn.daycode.fatalism.depository.common.constant;


public enum DepositoryRequestTypeCode {

    CREATE("CREATE", "新增标的"),


    TRADING("TRADING", "用户投标"),

    FULL_LOAN("FULL_LOAN", "审核标满放款"),

    MODIFY_STATUS("MODIFY_STATUS", "修改标的状态"),

	CONSUMER_CREATE("CONSUMER_CREATE", "开户"),
	RECHARGE("RECHARGE", "充值"),
	WITHDRAW("WITHDRAW", "提现"),
    ;

    private String code;
    private String desc;

    DepositoryRequestTypeCode(String code, String desc) {
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
