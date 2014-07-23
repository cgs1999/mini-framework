package com.duoduo.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;

/**
 * 日期 & 时间处理帮助类
 * @author chengesheng@gmail.com
 * @date 2014-6-2 下午11:02:53
 * @version 1.0.0
 */
public class DateUtils {

	private static final TimeZone TIMEZONE_GMT8 = TimeZone.getTimeZone("GMT+8");
	private static final String DATE_FORMAT_STRING = "yyyy-MM-dd";
	private static final String DATETIME_FORMAT_STRING = "yyyy-MM-dd HH:mm:ss";

	/** 由于DateFormat不是线程安全的，存在性能问题，故设计为ThreadLocal */
	private static ThreadLocal<DateFormat> dateFormat = new ThreadLocal<DateFormat>() {

		protected synchronized DateFormat initialValue() {
			return new SimpleDateFormat(DATE_FORMAT_STRING);
		}
	};

	/** 由于DateFormat不是线程安全的，存在性能问题，故设计为ThreadLocal */
	private static ThreadLocal<DateFormat> datetimeFormat = new ThreadLocal<DateFormat>() {

		protected synchronized DateFormat initialValue() {
			return new SimpleDateFormat(DATETIME_FORMAT_STRING);
		}
	};

	private static DateFormat getDateFormat() {
		return dateFormat.get();
	}

	private static DateFormat getDatetimeFormat() {
		return datetimeFormat.get();
	}

	/**
	 * 转换日期对象为yyyy-MM-dd格式的字符串，GMT8时区
	 */
	public static String toDateString(Date date) {
		getDateFormat().setTimeZone(TIMEZONE_GMT8);
		return date == null ? StringUtils.EMPTY : getDateFormat().format(date);
	}

	/**
	 * 转换日期对象为yyyy-MM-dd hh:mm:ss格式的字符串，GMT8时区
	 */
	public static String toDatetimeString(Date datetime) {
		getDatetimeFormat().setTimeZone(TIMEZONE_GMT8);
		return datetime == null ? StringUtils.EMPTY : getDatetimeFormat().format(datetime);
	}

	/**
	 * 转换日期对象为指定时区yyyy-MM-dd格式的字符串
	 * @param timezone 类似于"GMT"、"GMT+8"、"GMT-4"的时区代码
	 */
	public static String toDateString(Date date, String timezone) {
		getDateFormat().setTimeZone(TimeZone.getTimeZone(timezone));
		return date == null ? StringUtils.EMPTY : getDateFormat().format(date);
	}

	/**
	 * 转换日期对象为指定时区yyyy-MM-dd hh:mm:ss格式的字符串
	 * @param timezone 类似于"GMT"、"GMT+8"、"GMT-4"的时区代码
	 */
	public static String toDatetimeString(Date datetime, String timezone) {
		getDatetimeFormat().setTimeZone(TimeZone.getTimeZone(timezone));
		return datetime == null ? StringUtils.EMPTY : getDatetimeFormat().format(datetime);
	}

	/**
	 * 将yyyy-MM-dd格式的字符串转换为日期对象，GMT8时区
	 */
	public static Date fromDateString(String dateString) throws ParseException {
		getDateFormat().setTimeZone(TIMEZONE_GMT8);
		return StringUtils.isEmpty(dateString) ? null : getDateFormat().parse(dateString);
	}

	/**
	 * 将yyyy-MM-dd hh:mm:ss格式的字符串转换为日期时间对象，GMT8时区
	 */
	public static Date fromDatetimeString(String datetimeString) throws ParseException {
		getDatetimeFormat().setTimeZone(TIMEZONE_GMT8);
		return StringUtils.isEmpty(datetimeString) ? null : getDatetimeFormat().parse(datetimeString);
	}

	/**
	 * 将yyyy-MM-dd格式的字符串转换为指定时区的日期对象
	 */
	public static Date fromDateString(String dateString, String timezone) throws ParseException {
		getDateFormat().setTimeZone(TimeZone.getTimeZone(timezone));
		return StringUtils.isEmpty(dateString) ? null : getDateFormat().parse(dateString);
	}

	/**
	 * 将yyyy-MM-dd hh:mm:ss格式的字符串转换为指定时区的日期时间对象
	 */
	public static Date fromDatetimeString(String datetimeString, String timezone) throws ParseException {
		getDatetimeFormat().setTimeZone(TimeZone.getTimeZone(timezone));
		return StringUtils.isEmpty(datetimeString) ? null : getDatetimeFormat().parse(datetimeString);
	}

	/**
	 * 日期对象去除时分秒
	 */
	public static Date truncate(Date datetime) {
		return org.apache.commons.lang.time.DateUtils.truncate(datetime, Calendar.DAY_OF_MONTH);
	}

	/** 获取当前时间戳并转换成yyyy-MM-dd hh:mm:ss格式的日期时间字符串 */
	public static String getCurrentDatetime() {
		return DateUtils.toDatetimeString(new Date());
	}

	/** 获取当前时间戳并转换成yyyy-MM-dd格式的日期字符串 */
	public static String getCurrentDate() {
		return DateUtils.toDateString(new Date());
	}

	/**
	 * Adds a number of days to a date returning a new object. The original date object is unchanged.
	 */
	public static Date addDays(Date datetime, int amount) {
		return org.apache.commons.lang.time.DateUtils.addDays(datetime, amount);
	}

	/**
	 * Adds a number of hours to a date returning a new object. The original date object is unchanged.
	 */
	public static Date addHours(Date datetime, int amount) {
		return org.apache.commons.lang.time.DateUtils.addHours(datetime, amount);
	}

	/**
	 * Adds a number of minutes to a date returning a new object. The original date object is unchanged.
	 */
	public static Date addMinutes(Date datetime, int amount) {
		return org.apache.commons.lang.time.DateUtils.addMinutes(datetime, amount);
	}

	/**
	 * 在指定的日期上加天数并转换成yyyy-MM-dd格式的字符串。
	 */
	public static String addDaysAndToDateString(Date datetime, int days) {
		return DateUtils.toDateString(DateUtils.addDays(datetime, days));
	}

	/**
	 * 在指定的日期时间上加天数并转换成yyyy-MM-dd hh:mm:ss格式的字符串。
	 */
	public static String addDaysAndToDatetimeString(Date datetime, int days) {
		return DateUtils.toDatetimeString(DateUtils.addDays(datetime, days));
	}

	/**
	 * 在指定的日期时间上加小时数并转换成yyyy-MM-dd hh:mm:ss格式的字符串。
	 */
	public static String addHoursAndToDatetimeString(Date datetime, int hours) {
		return DateUtils.toDatetimeString(DateUtils.addHours(datetime, hours));
	}

	/**
	 * 在指定的日期时间上加分钟数并转换成yyyy-MM-dd hh:mm:ss格式的字符串。
	 */
	public static String addMinutesAndToDatetimeString(Date datetime, int minutes) {
		return DateUtils.toDatetimeString(DateUtils.addMinutes(datetime, minutes));
	}

	/**
	 * 判断给定第一个字符串形式时间是否在第二个时间之前
	 */
	public static boolean before(String datetimeString1, String datetimeString2) throws ParseException {
		return DateUtils.fromDatetimeString(datetimeString1).before(DateUtils.fromDatetimeString(datetimeString2));
	}

	/**
	 * 判断给定第一个字符串形式时间是否在第二个时间之后
	 */
	public static boolean after(String datetimeString1, String datetimeString2) throws ParseException {
		return DateUtils.fromDatetimeString(datetimeString1).after(DateUtils.fromDatetimeString(datetimeString2));
	}

	public static void main(String[] args) throws Exception {
		Date date = new Date();
		System.out.println("toDateString: " + DateUtils.toDateString(date));
		System.out.println("toDatetimeString: " + DateUtils.toDatetimeString(date));
		System.out.println("toDateString GMT: " + DateUtils.toDateString(date, "GMT"));
		System.out.println("toDatetimeString GMT: " + DateUtils.toDatetimeString(date, "GMT"));
		System.out.println("fromDatetimeString GMT: "
				+ DateUtils.toDatetimeString(DateUtils.fromDatetimeString(DateUtils.toDatetimeString(date), "GMT")));
		System.out.println("truncate: " + DateUtils.toDatetimeString(DateUtils.truncate(date)));
		System.out.println("getCurrentDatetime: " + DateUtils.getCurrentDatetime());
		System.out.println("getCurrentDate: " + DateUtils.getCurrentDate());
		System.out.println("addDaysAndToDateString 7: " + DateUtils.addDaysAndToDateString(date, 7));
		System.out.println("addDaysAndToDateString -2: " + DateUtils.addDaysAndToDateString(date, -2));
		System.out.println("addDaysAndToDatetimeString 7: " + DateUtils.addDaysAndToDatetimeString(date, 7));
		System.out.println("addDaysAndToDatetimeString -2: " + DateUtils.addDaysAndToDatetimeString(date, -2));
		System.out.println("addHoursAndToDatetimeString 7: " + DateUtils.addHoursAndToDatetimeString(date, 7));
		System.out.println("addHoursAndToDatetimeString -2: " + DateUtils.addHoursAndToDatetimeString(date, -2));
		System.out.println("addMinutesAndToDatetimeString 7: " + DateUtils.addMinutesAndToDatetimeString(date, 7));
		System.out.println("addMinutesAndToDatetimeString -2: " + DateUtils.addMinutesAndToDatetimeString(date, -2));
		System.out.println("before: "
				+ DateUtils.before(DateUtils.toDatetimeString(date), DateUtils.addMinutesAndToDatetimeString(date, 2)));
		System.out.println("after: "
				+ DateUtils.after(DateUtils.toDatetimeString(date), DateUtils.addMinutesAndToDatetimeString(date, -2)));
	}

}
