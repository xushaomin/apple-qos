package com.appleframework.qos.server.statistics.utils;

import java.util.Date;

public class DateUtil {

	public static Date getToday() {
		Date now = new Date();
		String nowStr = DateFormatUtil.toString(now, DateFormatUtil.pattern10);
		return DateFormatUtil.toDate(nowStr, DateFormatUtil.pattern10);
	}
}
