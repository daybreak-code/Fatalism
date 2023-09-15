package cn.daycode.fatalism.transaction.common.constant;

import cn.daycode.fatalism.common.domain.ErrorCode;

public enum TransactionErrorCode implements ErrorCode {

    E_150101(150101, "标的编码不存在"),

    E_150102(150102, "根据标的编码修改标的可用状态失败"),

    E_150103(150103, "请求流水号不存在"),

    E_150104(150104, "根据请求流水号修改投标信息可用状态失败"),


    E_150105(150105, "查询的用户不是投资人"),

    E_150107(150107, "管理员审核标的失败"),

    E_150108(150108, "身份校验失败不是借款人"),

    E_150109(150109, "投资金额小于最小投标金额, 不允许进行投标操作"),

    E_150110(150110, "本次投标资金额超出标的剩余未投资金"),

    E_150111(150111, "此次投标后 标的剩余金额不满足最小投标注金额, 请修改金额后操作"),

    E_150112(150112, "用户余额不足, 请充值"),

    E_150113(150113, "存管代理服务, 签名验证失败"),

    E_150114(150114, "此标的 已满标"),

    E_150115(150115, "修改标的状态失败"),

    E_150116(150116, "生成还款计划、应收明细失败 "),

    E_150117(150117, "审核满标放款失败-还款服务响应异常"),
    E_150118(150118, "不能投资自己的借款"),
    E_150119(150119, "用户有待审核的借款信息，不能再次借款"),
    ;

    private int code;
    private String desc;

    TransactionErrorCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }


    public static TransactionErrorCode setErrorCode(int code) {
        for (TransactionErrorCode errorCode : TransactionErrorCode.values()) {
            if (errorCode.getCode() == code) {
                return errorCode;
            }
        }
        return null;
    }
}

