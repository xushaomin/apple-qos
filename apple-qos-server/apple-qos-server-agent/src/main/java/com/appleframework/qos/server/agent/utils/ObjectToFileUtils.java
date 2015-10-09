package com.appleframework.qos.server.agent.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;

public class ObjectToFileUtils {
	
	protected static final Logger logger = Logger.getLogger(ObjectToFileUtils.class);

	public static void writeObject(Object object, String filePath) {  
		try {
            FileOutputStream outStream = new FileOutputStream(filePath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outStream);  
            objectOutputStream.writeObject(object);  
            outStream.close();  
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
    }  
      
    @SuppressWarnings("resource")
	public static Object readObject(String filePath){
    	FileInputStream freader;
    	try {
    		freader = new FileInputStream(filePath);  
            ObjectInputStream objectInputStream = new ObjectInputStream(freader);  
            return objectInputStream.readObject();  
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage());
		}
    	return null;
    }  
      
}
