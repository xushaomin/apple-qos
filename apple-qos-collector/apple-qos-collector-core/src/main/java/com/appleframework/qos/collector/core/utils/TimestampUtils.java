package com.appleframework.qos.collector.core.utils;

import java.util.Date;

public class TimestampUtils {

	// 产生5分钟的时间戳,传入时间格式：yyyyMMddHHmm
	@SuppressWarnings("deprecation")
	public static String genFiveMinuteTimestamp(String timestamp) {
		int minutes = 0;
		String fiveMinute = null;
		Date date = null;
		if (timestamp.startsWith("2")) {
			date = DateFormatUtils.toDate(timestamp, DateFormatUtils.pattern12);
		} else {
			date = new Date(Long.parseLong(timestamp));
		}
		minutes = date.getMinutes() + 1;

		if (minutes >= 0 && minutes <= 5) {
			fiveMinute = "05";
		} else if (minutes > 5 && minutes <= 10) {
			fiveMinute = "10";
		} else if (minutes > 10 && minutes <= 15) {
			fiveMinute = "15";
		} else if (minutes > 15 && minutes <= 20) {
			fiveMinute = "20";
		} else if (minutes > 20 && minutes <= 25) {
			fiveMinute = "25";
		} else if (minutes > 25 && minutes <= 30) {
			fiveMinute = "30";
		} else if (minutes > 30 && minutes <= 35) {
			fiveMinute = "35";
		} else if (minutes > 35 && minutes <= 40) {
			fiveMinute = "40";
		} else if (minutes > 40 && minutes <= 45) {
			fiveMinute = "45";
		} else if (minutes > 45 && minutes <= 50) {
			fiveMinute = "50";
		} else if (minutes > 50 && minutes <= 55) {
			fiveMinute = "55";
		} else {
			fiveMinute = "00";
		}
		fiveMinute = timestamp.substring(0, 10) + fiveMinute;
		timestamp = fiveMinute;
		return timestamp;
	}

	// 产生10分钟的时间戳,传入时间格式：yyyyMMddHHmm
	public static String genTenMinuteTimestamp(String timestamp) {
		if (timestamp.startsWith("2")) {
			if(timestamp.length() == 12) {
				return timestamp.substring(0, 11) + "0";
			}
			else {
				return timestamp;
			}
		} else {
			Date date = new Date(Long.parseLong(timestamp));
			String tt = DateFormatUtils.toString(date, DateFormatUtils.pattern12);
			return tt.substring(0, 11) + "0";
		}
	}
}
