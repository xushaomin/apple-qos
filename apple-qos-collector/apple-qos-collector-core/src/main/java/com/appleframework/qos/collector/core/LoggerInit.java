package com.appleframework.qos.collector.core;

import java.io.File;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.appleframework.qos.collector.core.utils.ApplicationUtils;
import com.appleframework.qos.collector.core.utils.DateFormatUtils;
import com.appleframework.qos.collector.core.utils.SystemPropertiesUtils;

/**
 * 
 * logger初始化，把日志输出到应用的目录里
 * 
 */
public class LoggerInit {
	
	private static Logger log = Logger.getLogger(LoggerInit.class);
	
	public static final String LOG_NAME_GOS_DATA = "QosDataLog";

    public static volatile boolean initOK = false;

    private static Properties defaultProperties = new Properties();
    
    public static long logFileGenStartTime = 0;
    
    public static int logFileGenHour = 1;
    
    public static int logFileGenDay  = 2;
    
    public static String LOG_FILE_GEN_TYPE_KEY = "log.file.gen.type";
    
    public static String LOG_FILE_GEN_PATH_KEY = "log.file.gen.path";
    
    public static String LOG_FILE_GEN_DEFAULT_PATH = "/work/logs/qos";
    
    public static long logFileGenIntervalTime = 86400000; //日志产生时间间隔 单位毫秒 默认为1天
    
    //一天86400000
	//一分钟 60000
    public static long logFileGenHourInterval = 3600000; //1小时日志产生时间间隔 单位毫秒
    public static long logFileGenDayInterval  = 86400000; //1天日志产生时间间隔 单位毫秒
    
    public static int logFileGenType = 2; //小时为单位 1为小时 2为天 默认为天
       
    static {
        defaultProperties.put("log4j.logger.QosDataLog", "ERROR, QosDataLogFile");
        defaultProperties.put("log4j.additivity.QosDataLog", "false");
        defaultProperties.put("log4j.appender.QosDataLogFile", "org.apache.log4j.FileAppender");
        //defaultProperties.put("log4j.appender.QosDataLogFile.DatePattern", "'.'yyyy-MM-dd");
        //defaultProperties.put("log4j.appender.QosDataLogFile.File", "qos_data.log");
        defaultProperties.put("log4j.appender.QosDataLogFile.layout", "org.apache.log4j.PatternLayout");
        //defaultProperties.put("log4j.appender.QosDataLogFile.layout.ConversionPattern", "%d{MM-dd HH:mm:ss} - %m%n");
        defaultProperties.put("log4j.appender.QosDataLogFile.layout.ConversionPattern", "%m%n");
        defaultProperties.put("log4j.appender.QosDataLogFile.Append", "true");
    }


    public static void initLogger() {

        if (initOK) {
            return;
        }

        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(LoggerInit.class.getClassLoader());

        try {
            
        	Date now = new Date();
        	String datePattern = DateFormatUtils.pattern8;
        	int logFileGenTypeP = logFileGenDay;
        	
        	String logFileGenType = SystemPropertiesUtils.getString(LOG_FILE_GEN_TYPE_KEY);
        	if(null != logFileGenType) {
        		logFileGenTypeP = Integer.parseInt(logFileGenType);
        	}
        	if(logFileGenTypeP == logFileGenHour) {
        		datePattern = "yyyyMMddHH";
        		logFileGenIntervalTime = logFileGenHourInterval;
        	}
        	
        	String strDate = DateFormatUtils.toString(now, datePattern);
        	String application = ApplicationUtils.getApplicationName();
        	logFileGenStartTime =  DateFormatUtils.toDate(strDate, datePattern).getTime();
            // 使缺省的配置生效(Logger, Appender)
            PropertyConfigurator.configure(defaultProperties);
            String date = DateFormatUtils.toString(now, datePattern);
            String logFileGenPath = getLogFileGenPath();
            String filePath = logFileGenPath + "/" + application + "_" + date + ".log";
            File logFile = new File(logFileGenPath);
            if(!logFile.exists()) {
            	logFile.mkdirs();
            }
            
            setFileAppender(filePath, LOG_NAME_GOS_DATA);

            initOK = true;
        }
        finally {
            Thread.currentThread().setContextClassLoader(cl);
        }
    }

    private static void setFileAppender(String logPath, String logName) {
        FileAppender qosFileAppender = getFileAppender(Logger.getLogger(logName));

        qosFileAppender.setFile(logPath);
        qosFileAppender.activateOptions(); // 很重要，否则原有日志内容会被清空
        log.warn("成功为" + logName + "添加Appender. 输出路径:" + logPath);
    }

    private static FileAppender getFileAppender(Logger logger) {
        FileAppender fileAppender = null;
        for (Enumeration<?> appenders = logger.getAllAppenders(); 
        		(null == fileAppender) && appenders.hasMoreElements();) {
            Appender appender = (Appender) appenders.nextElement();
            if (FileAppender.class.isInstance(appender)) {
                fileAppender = (FileAppender) appender;
            }
        }
        return fileAppender;
    }
    
    private static String getLogFileGenPath() {
        String path = SystemPropertiesUtils.getString(LOG_FILE_GEN_PATH_KEY);
        if(null == path) {
        	return LOG_FILE_GEN_DEFAULT_PATH;
        }
        else {
        	return path;
        }
    }
    
    
}
