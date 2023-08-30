package cn.daycode.fatalism.common.util;


import org.springframework.util.StringUtils;

public class CommonUtil {

    public static String hiddenMobile(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return "";
        }
        return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
    }
}
