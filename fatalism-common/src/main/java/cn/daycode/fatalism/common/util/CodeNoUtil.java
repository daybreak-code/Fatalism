package cn.daycode.fatalism.common.util;

import cn.daycode.fatalism.common.domain.CodePrefixCode;

import java.util.UUID;

public class CodeNoUtil {

    public static String getNo(CodePrefixCode prefixCode) {

        return prefixCode.getCode() + UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }

    public static void main(String[] args) {
        System.out.println(CodeNoUtil.getNo(CodePrefixCode.CODE_CONSUMER_PREFIX));
    }
}
