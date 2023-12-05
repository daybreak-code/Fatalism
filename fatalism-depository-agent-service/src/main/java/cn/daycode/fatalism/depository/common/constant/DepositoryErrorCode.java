
package cn.daycode.fatalism.depository.common.constant;


import cn.daycode.fatalism.common.domain.ErrorCode;

public enum DepositoryErrorCode implements ErrorCode {

    E_160101(160101, "存管代理服务校验存管系统返回数据签名失败"),
    E_160102(160102, "交易记录已存在"),
    E_160103(160103, "交易正在执行"),
	;

    private int code;
    private String desc;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    DepositoryErrorCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public static DepositoryErrorCode setErrorCode(int code) {
        for (DepositoryErrorCode errorCode : DepositoryErrorCode.values()) {
            if (errorCode.getCode() == code) {
                return errorCode;
            }
        }
        return null;
    }
}
