package com.appleframework.qos.collector.core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @author xusm(2012-11-22)
 *
 */
public class SystemPropertiesUtils {
	
	protected static final Logger log = Logger.getLogger(SystemPropertiesUtils.class);
	
	private static final String SYSTEM_PROPERTIES = "system.properties";
		
	private static Properties prop = null;
	
	/*public PropertiesConfig(File file){
		load(file);
	}*/
	
	static {
		load(ResourceUtils.getAsStream(SYSTEM_PROPERTIES));
	}
	
	public SystemPropertiesUtils(String name) {
				
	}
	
	private static void load(InputStream is){
		prop = new Properties();
		try {
			prop.load(is);
		} catch (IOException e) {			
			log.error("error happen when loading properties file:", e);
		}
	}
	
	public static String getString(String key){
		return prop.getProperty(key);
	}
	
}
