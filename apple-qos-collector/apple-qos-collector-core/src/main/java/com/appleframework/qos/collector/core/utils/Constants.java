package com.appleframework.qos.collector.core.utils;

import java.util.regex.Pattern;

public class Constants {
	
	public static final Pattern COMMA_SPLIT_PATTERN = Pattern.compile("\\s*[,]+\\s*");

	public static final String APPLICATION_NAME = "application.name";
	
	public static final String LOCALHOST_KEY  = "localhost";
	
	public static final String DEFAULT_KEY_PREFIX = "default.";
	
	public static final String ANYHOST_KEY = "anyhost";

    public static final String ANYHOST_VALUE = "0.0.0.0";
    	
	public static final String KEY_COUNT_PROTOCOL = "c";

    public static final String KEY_ERROR_CODE = "ec";
    
    public static final String KEY_PROVIDER_APP = "pp";
    
    public static final String KEY_PROVIDER_ADD = "pd";
    
    public static final String KEY_CONSUMER_APP = "cp";
    
    public static final String KEY_CONSUMER_ADD = "cd";
    
    public static final String KEY_INTERFACE = "i";

    public static final String KEY_METHOD = "m";
    
    public static final String KEY_TIMESTAMP = "t";

    public static final String KEY_SUCCESS = "s";

    public static final String KEY_FAILURE = "f";

    public static final String KEY_ELAPSED = "e";

    public static final String KEY_MAX_ELAPSED = "me";
    
    public static final String KEY_COLLECT_TYPE = "ct";
    
    public static boolean COLLECT_START = true;
    
    public static final int COLLECT_TYPE_PROVIDER = 1;
    public static final int COLLECT_TYPE_CONSUMER = 2;
    public static final int COLLECT_TYPE_LOCAL    = 3;
    

	public static String FILE_SUBFFIX_LOG   = ".log";
	public static String FILE_SUBFFIX_INDEX = ".index";
	
	public static String BASE_FILE_PATH = "/work/logs/qos/";

}
