package com.appleframework.qos.collector.core.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ApplicationUtils {

	public static String APPLICATION_NAME_KEY = "application.name";

	public static String getApplicationName() {
		String name = SystemPropertiesUtils.getString(APPLICATION_NAME_KEY);
		if (null == name) {
			try {
				return InetAddress.getLocalHost().getHostName();
			} catch (UnknownHostException e) {
				return null;
			}
		} else {
			return name;
		}
	}

}