package com.appleframework.qos.server.agent.task;

import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.appleframework.qos.collector.core.utils.Constants;
import com.appleframework.qos.collector.core.utils.DateFormatUtils;
import com.appleframework.qos.server.agent.LogFileReadStarter;
import com.appleframework.qos.server.agent.LogFileReadThread;
import com.appleframework.qos.server.agent.model.ThreadStatus;

@Component("fileTask")
public class FileTask {

	private static Logger logger = Logger.getLogger(FileTask.class);

	@Scheduled(cron = "0 0 0,8,16 * * ?")
	public void task() {
		
		logger.info("--->delete file task time: " + 
				DateFormatUtils.toString(new Date(), DateFormatUtils.pattern19));
		
		Map<Thread, StackTraceElement[]> threadMap = Thread.getAllStackTraces();
		Iterator<Map.Entry<Thread, StackTraceElement[]>> it = threadMap.entrySet().iterator();
        while(it.hasNext()){
             
            try {
            	 Entry<Thread, StackTraceElement[]> entry = it.next();
                 Thread thread = entry.getKey();
                 String name = thread.getName();
                 
                 AtomicReference<String> reference = LogFileReadStarter.threadMap.get(name);
                 if (reference == null) {
                	 continue;
                 }
                 String currentName = reference.get();
                 
                 if(null != currentName && !currentName.equals(name)) {
                	 continue;
                 }
                 
                 LogFileReadThread logFileThread = (LogFileReadThread)entry.getKey();
                 ThreadStatus status = logFileThread.getReference().get();
                 long now = System.currentTimeMillis();
                 long lastReaded = status.getLastReaded();
                 
            	//进程停止 文件删除
                 //86400000 为一天
                 //14400000 为4小时
                if(now - lastReaded > 14400000) {
                	
                	if(!logFileThread.isInterrupted()) {
                		logger.info("----------线程" + name + "被终止");
                		logFileThread.setDone(true);
                		logFileThread.interrupt();
                		logFileThread.getRandomFile().getChannel().close();
                		logFileThread.getRandomFile().close();
                	}
                	
                	//if(thread.isInterrupted()) {
                		logger.info("----------线程终止后，删除文件开始");
                		try {
                			LogFileReadStarter.threadMap.remove(name);
                		} catch (Exception e) {
                			logger.error(e.getMessage(), e);
						}
                		
                		String indexFilePath = Constants.BASE_FILE_PATH + name + Constants.FILE_SUBFFIX_INDEX;
                		String logFilePath = Constants.BASE_FILE_PATH + name + Constants.FILE_SUBFFIX_LOG;
                		
                		File indexFile = new File(indexFilePath);
                		File logFile = new File(logFilePath);
                		
                		if (indexFile.isFile() && indexFile.exists()) {
                			indexFile.delete();
                			logger.info("----------线程终止后，删除文件 + " + indexFilePath);
                		}
                		
                		if (logFile.isFile() && logFile.exists()) {
                			logFile.delete();
                			logger.info("----------线程终止后，删除文件 + " + logFilePath);
                		}
                		it.remove();
                	//}
                	
                }
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
            
		}
		
		
	}

}