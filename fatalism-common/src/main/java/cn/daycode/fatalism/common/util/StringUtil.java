package cn.daycode.fatalism.common.util;


import java.io.File;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    /**
     * yyyy-MM-dd日期格式
     */
    public static final SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");

    public static final String SPACE = " ";
    public static final String DOT = ".";
    public static final String SLASH = "/";
    public static final String BACKSLASH = "\\";
    public static final String EMPTY = "";
    public static final String CRLF = "\r\n";
    public static final String NEWLINE = "\n";
    public static final String UNDERLINE = "_";
    public static final String COMMA = ",";

    public static final String HTML_NBSP = "&nbsp;";
    public static final String HTML_AMP = "&amp";
    public static final String HTML_QUOTE = "&quot;";
    public static final String HTML_LT = "&lt;";
    public static final String HTML_GT = "&gt;";

    public static final String EMPTY_JSON = "{}";


    public static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0;
    }


    public static boolean isNotBlank(String str) {
        return false == isBlank(str);
    }


    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }


    public static boolean isNotEmpty(String str) {
        return false == isEmpty(str);
    }



    public static boolean isWrap(String str, String prefix, String suffix) {
        return str.startsWith(prefix) && str.endsWith(suffix);
    }


    public static boolean isWrap(String str, String wrapper) {
        return isWrap(str, wrapper, wrapper);
    }


    public static boolean isWrap(String str, char wrapper) {
        return isWrap(str, wrapper, wrapper);
    }


    public static boolean isWrap(String str, char prefixChar, char suffixChar) {
        return str.charAt(0) == prefixChar && str.charAt(str.length() - 1) == suffixChar;
    }


    public static String padPre(String str, int minLength, char padChar) {
        if (str.length() >= minLength) {
            return str;
        }
        StringBuilder sb = new StringBuilder(minLength);
        for (int i = str.length(); i < minLength; i++) {
            sb.append(padChar);
        }
        sb.append(str);
        return sb.toString();
    }


    public static String padEnd(String str, int minLength, char padChar) {
        if (str.length() >= minLength) {
            return str;
        }
        StringBuilder sb = new StringBuilder(minLength);
        sb.append(str);
        for (int i = str.length(); i < minLength; i++) {
            sb.append(padChar);
        }
        return sb.toString();
    }


    public static StringBuilder builder() {
        return new StringBuilder();
    }


    public static StringBuilder builder(int capacity) {
        return new StringBuilder(capacity);
    }


    public static StringBuilder builder(String... strs) {
        final StringBuilder sb = new StringBuilder();
        for (String str : strs) {
            sb.append(str);
        }
        return sb;
    }


    public static byte[] bytes(String str, String charset) {
        if (null == str) {
            return null;
        }
        if (isBlank(charset)) {
            return null;
        }
        return str.getBytes(Charset.forName(charset));
    }


    public static boolean isInteger(String input) {
        Matcher mer = Pattern.compile("^[+-]?[0-9]+$").matcher(input);
        return mer.find();
    }


    public static Integer[] stringToIntegerArray(String[] str) {
        Integer array[] = new Integer[str.length];
        for (int i = 0; i < str.length; i++) {
            array[i] = Integer.parseInt(str[i]);
        }
        return array;
    }


    public static Long[] stringTOLongArray(String[] str) {
        Long array[] = new Long[str.length];
        for (int i = 0; i < str.length; i++) {
            array[i] = Long.parseLong(str[i]);
        }
        return array;
    }


    public static String getFileExt(String src) {

        String filename = src.substring(src.lastIndexOf(File.separator) + 1, src.length());// 获取到文件名

        return filename.substring(filename.lastIndexOf(".") + 1);
    }


    public static String getFileName(String src) {

        String filename = src.substring(src.lastIndexOf(File.separator) + 1, src.length());// 获取到文件名

        return filename.substring(0, filename.lastIndexOf("."));
    }


    public static boolean isNull(String src) {

        return src == null || src.length() == 0 || src.trim().length() == 0;
    }


    public static Boolean checkArrayValue(String[] arr, String checkValue) {
        Boolean checkFlag = false;
        if (arr != null && arr.length > 0) {
            for (int i = 0; i < arr.length; i++) {
                if (arr[i].equals(checkValue)) {
                    checkFlag = true;
                    break;
                }
            }
        }
        return checkFlag;
    }


    private static Pattern patternForUTC = Pattern.compile("_([a-z]){1}");


    public static String mapUnderscoreToCamelCase(String str) {
        // 先转成全小写
        str = str.toLowerCase();
        final Matcher matcher = patternForUTC.matcher(str);
        while (matcher.find()) {
            str = str.replaceAll(matcher.group(), matcher.group(1).toUpperCase());
        }
        return str;
    }


    public static String mapCamelCaseToUnderscore(String str) {
        return str.replaceAll("([A-Z]){1}","_$1").toUpperCase();
    }
}
