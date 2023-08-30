package cn.daycode.fatalism.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DateUtil {

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static final String YYYYMMDD = "yyyyMMdd";

    public static final String HHmmss = "HHmmss";

    public static final String YYYYMM = "yyyyMM";

    private DateUtil(){
    }

    public static LocalDateTime now(){
        return LocalDateTime.now();
    }

    public static String toDateTime(LocalDateTime date){
        return toDateTime(date, YYYY_MM_DD_HH_MM_SS);
    }

    public static String toDateTime(LocalDateTime dateTime, String pattern) {
        return dateTime.format(DateTimeFormatter.ofPattern(pattern, Locale.SIMPLIFIED_CHINESE));
    }

    public static String toDateText(LocalDate date, String pattern){
        if (date == null || pattern == null){
            return null;
        }
        return date.format(DateTimeFormatter.ofPattern(pattern, Locale.SIMPLIFIED_CHINESE));
    }

    public static Date addExtraHour(Date date, int hour){
        Calendar cal = Calendar.getInstance();
        if (date != null){
            cal.setTime(date);
        }
        cal.add(Calendar.HOUR_OF_DAY, hour);
        return cal.getTime();
    }

    public static Date increaseDay2Date(Date date, int increase){
        Calendar cal = Calendar.getInstance();
        if (date != null){
            cal.setTime(date);
        }
        cal.add(Calendar.DAY_OF_MONTH, increase);
        return cal.getTime();
    }

    public static LocalDateTime localDateTimeAddMonth(LocalDateTime date, int increase){
        java.util.Date temp = Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
        Calendar cal = Calendar.getInstance();
        if (temp != null) {
            cal.setTime(temp);
        }
        cal.add(Calendar.MONTH, increase);
        return LocalDateTime.ofInstant(cal.getTime().toInstant(), ZoneId.systemDefault());
    }

    public static Date format(String strDate, String format) {
        Date d = null;
        if (null == strDate || "".equals(strDate))
            return null;
        else
            try {
                d = getFormatter(format).parse(strDate);
            } catch (ParseException pex) {
                return null;
            }
        return d;
    }


    private static SimpleDateFormat getFormatter(String parttern) {
        return new SimpleDateFormat(parttern);
    }


    public static List<String> getAllDaysOfMonthInString(Date month, DateFormat dateFormat) {
        List<String> rs = new ArrayList<String>();
        DateFormat df = null;
        if (null == dateFormat) {
            df = new SimpleDateFormat("yyyy-MM-dd");
        }
        Calendar cad = Calendar.getInstance();
        if (null != month) {
            cad.setTime(month);
        }
        int day_month = cad.getActualMaximum(Calendar.DAY_OF_MONTH); // 获取当月天数
        for (int i = 0; i < day_month; i++) {
            cad.set(Calendar.DAY_OF_MONTH, i + 1);
            rs.add(df.format(cad.getTime()));

        }
        return rs;
    }


    public static List<Date> getAllDaysOfMonth(Date month) {
        List<Date> rs = new ArrayList<Date>();
        Calendar cad = Calendar.getInstance();
        if (null != month) {
            cad.setTime(month);
        }
        int day_month = cad.getActualMaximum(Calendar.DAY_OF_MONTH); // 获取当月天数
        for (int i = 0; i < day_month; i++) {
            cad.set(Calendar.DAY_OF_MONTH, i + 1);
            rs.add(cad.getTime());

        }
        return rs;
    }


    public static List<String> getSpecifyDaysOfMonthInString(Date begin, Date end, DateFormat dateFormat) {
        DateFormat df = null;
        if (null == dateFormat) {
            df = new SimpleDateFormat("yyyy-MM-dd");
        }
        List<String> rs = new ArrayList<String>();
        List<Date> tmplist = getSpecifyDaysOfMonth(begin, end);
        for (Date date : tmplist)
            rs.add(df.format(date));
        return rs;
    }


    public static List<Date> getSpecifyDaysOfMonth(Date begin, Date end) {
        List<Date> rs = new ArrayList<Date>();
        Calendar cad = Calendar.getInstance();
        int day_month = -1;
        if (null == begin) {// 设置开始日期为指定日期
            // day_month = cad.getActualMaximum(Calendar.DAY_OF_MONTH); // 获取当月天数
            cad.set(Calendar.DAY_OF_MONTH, 1);// 设置开始日期为当前月的第一天
            begin = cad.getTime();
        }
        cad.setTime(begin);
        if (null == end) {// 如果结束日期为空 ，设置结束日期为下月的第一天
            day_month = cad.getActualMaximum(Calendar.DAY_OF_MONTH); // 获取当月天数
            cad.set(Calendar.DAY_OF_MONTH, day_month + 1);
            end = cad.getTime();
        }
        cad.set(Calendar.DAY_OF_MONTH, 1);// 设置开始日期为当前月的第一天
        Date tmp = begin;
        int i = 1;
        while (true) {
            cad.set(Calendar.DAY_OF_MONTH, i);
            i++;
            tmp = cad.getTime();
            if (tmp.before(end)) {
                rs.add(cad.getTime());
            } else {
                break;
            }
        }
        return rs;
    }


    public static synchronized Date getCurrDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    public static String format(Date date, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }


    public static String getCurrDateTimeStr() {
        return format(getCurrDate(), YYYY_MM_DD_HH_MM_SS);
    }


    public static String getSpecifiedDayBefore(String specifiedDay, String formatStr) {// 可以用new
        // Date().toLocalString()传递参数
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS).parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);

        String dayBefore = new SimpleDateFormat(formatStr).format(c.getTime());
        return dayBefore;
    }


    public static String getSpecifiedDayAfter(String specifiedDay, String formatStr) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS).parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);

        String dayAfter = new SimpleDateFormat(formatStr).format(c.getTime());
        return dayAfter;
    }


    public static final String getWeekFirstDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 2;
        cal.add(Calendar.DATE, -day_of_week);
        return sdf.format(cal.getTime());
    }


    public static final String getCurrentMonthFirstDay() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        cal.set(GregorianCalendar.DAY_OF_MONTH, 1);
        Date beginTime = cal.getTime();
        return sdf.format(beginTime);
    }


    public static final String getYesterdayStart() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }

    public static final String getYesterdayEnd() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime()) + " 23:59:59";
    }

    public static final String getCurrDayStart() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }


    public static final String getStartDayWithMonth(String month) throws ParseException {
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat mf = new SimpleDateFormat("yyyy-MM");
        Date date = mf.parse(month);
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 0);// 因为格式化时默认了DATE为本月第一天所以此处为0
        return sdf.format(calendar.getTime());
    }


    public static final String getEndDayWithMonth(String month) throws ParseException {
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat mf = new SimpleDateFormat("yyyy-MM");
        Date date = mf.parse(month);
        calendar.setTime(date);
        calendar.roll(Calendar.DATE, -1);// api解释roll()：向指定日历字段添加指定（有符号的）时间量，不更改更大的字段
        return sdf.format(calendar.getTime());
    }

    public static final String formatYearMonthDay(String dateStr) throws ParseException {
        if (StringUtil.isNotBlank(dateStr)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(dateStr);
            return sdf.format(date);
        } else {
            return "";
        }
    }


    public static final int getWeekIndexOfMonth(String dateStr) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(dateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);
        return weekOfMonth;
    }


    public static final int getSecondToDesignationTime(String designationTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date toDate;
        try {
            toDate = dateFormat.parse(designationTime);
            int u = (int) ((toDate.getTime() - dateFormat.parse(DateUtil.getCurrDateTimeStr()).getTime()) / 1000);
            return u;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static final int getYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(cal.YEAR);
    }

    public static final int getMonth() {
        Calendar cal = Calendar.getInstance();
        return cal.get(cal.MONTH) + 1;
    }

    public static final int getDay() {
        Calendar cal = Calendar.getInstance();
        return cal.get(cal.DATE);
    }


    public static int differentDaysByMillisecond(LocalDateTime start, LocalDateTime end) {
        return (int) ((end.toInstant(ZoneOffset.of("+8")).toEpochMilli() - start.toInstant(ZoneOffset.of("+8")).toEpochMilli()) / (1000 * 3600 * 24));
    }
}
