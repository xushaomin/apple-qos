package com.appleframework.qos.collector.core.utils;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * get resource from classpath, file, jar...
 * @author xusm(2012-4-9)
 *
 */
public class ResourceUtils {
		
	/** classpath prefix */
	public static final String CLASSPATH_URL_PREFIX = "classpath:";	

	/**
	 * get a ClassLoader
	 * @return
	 */
	public static ClassLoader getClassLoader() {
		ClassLoader cl = null;
		cl = Thread.currentThread().getContextClassLoader();		
		if (cl == null) {
			// No thread context class loader, use class loader of this class.
			cl = ResourceUtils.class.getClassLoader();
		}
		return cl;
	}
	
	/**
	 * get config file name as URL
	 * @param configFileName
	 * @return
	 * @throws Exception
	 */
	public static URL getAsUrl(String configFileName){
		if (configFileName.startsWith(CLASSPATH_URL_PREFIX)) {
			configFileName = configFileName.substring(CLASSPATH_URL_PREFIX.length());
		}
		URL url = getClassLoader().getResource(configFileName);
		if (url == null) {
			System.out.println("config file: {} cannot be found! " +  configFileName);			
		}
		return url;
	}
	
	/**
	 * get config file name as File
	 * 
	 * @param file
	 * @return	
	 */
	public static File getAsFile(String configFileName){
		URL url = getAsUrl(configFileName);
		if(url != null){
			URI uri = toURI(url);		
			if(uri != null){
				return new File(uri.getSchemeSpecificPart());
			}
		}
		return null;
	}

	public static URI toURI(URL url) {
		try {
			return new URI(url.toString().replaceAll(" ", "%20"));
		} catch (URISyntaxException e) {
			return null;
		}
	}
	
	public static InputStream getAsStream(String name){
		return getClassLoader().getResourceAsStream(name);
	}
}
