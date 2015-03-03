package com.appleframework.qos.server.agent;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.log4j.Logger;

import com.appleframework.qos.collector.core.Statistic;
import com.appleframework.qos.collector.core.URL;
import com.appleframework.qos.collector.core.utils.Constants;
import com.appleframework.qos.collector.core.utils.DateFormatUtils;
import com.appleframework.qos.server.agent.model.ThreadStatus;
import com.appleframework.qos.server.agent.utils.ObjectToFileUtils;

/**
 * AgentMonitor
 * 
 * @author cruise.xu
 */
public class AgentMonitor {
    
	private static Logger logger = Logger.getLogger(AgentMonitor.class);
        
    private final MonitorSendService monitorSendService;
    
    private volatile ConcurrentMap<String, AtomicReference<Statistic>> statisticsMap 
    		= new ConcurrentHashMap<String, AtomicReference<Statistic>>();

    public AgentMonitor(MonitorSendService monitorSendService) {
        this.monitorSendService = monitorSendService;
    }    
    
    public synchronized void send() {
    	long now = System.currentTimeMillis();
    	//发送数据
    	logger.info("--->send task time: " + 
				DateFormatUtils.toString(new Date(), DateFormatUtils.pattern19));
    	Iterator<Map.Entry<String, AtomicReference<Statistic>>> it = statisticsMap.entrySet().iterator();  
        while(it.hasNext()){
            Map.Entry<String, AtomicReference<Statistic>> entry = it.next();           
            AtomicReference<Statistic> reference = entry.getValue();
            Statistic statistic = reference.get();
            int success = statistic.getSuccess();
            int failure = statistic.getFailure();
            int elapsed = statistic.getElapsed();
            int maxElapsed = statistic.getMaxElapsed();
            String errorCode = statistic.getErrorCode();
            int type = statistic.getType();
            String timestamp = statistic.getTimestamp();
            
            Date statDate = DateFormatUtils.toDate(timestamp, DateFormatUtils.pattern12);
            if(now - statDate.getTime() < 60000) {
            	continue;
            }
             
            if(success == 0 && failure == 0)
            	continue;
            // 发送汇总信息
            URL url = new URL(Constants.KEY_COUNT_PROTOCOL, statistic.getHost(), statistic.getPort())
            		.addParameterString(statistic.toIdentityKey())
                    .addParameters(Constants.KEY_TIMESTAMP,  String.valueOf(timestamp),
                    		Constants.KEY_SUCCESS, String.valueOf(success),
                    		Constants.KEY_FAILURE, String.valueOf(failure), 
                    		Constants.KEY_ELAPSED, String.valueOf(elapsed),
                    		Constants.KEY_MAX_ELAPSED, String.valueOf(maxElapsed),
                    		Constants.KEY_ERROR_CODE, String.valueOf(errorCode),
                    		Constants.KEY_COLLECT_TYPE, String.valueOf(type)
                            );
            monitorSendService.send(url);
            logger.info("-------------->>>" + url.toFullString());
            
            it.remove();
            
        }
        
        //保存offset
        logger.info("--->save offset task time: " + 
				DateFormatUtils.toString(new Date(), DateFormatUtils.pattern19));
		
        Map<Thread, StackTraceElement[]> threadMap = Thread.getAllStackTraces();
		Iterator<Map.Entry<Thread, StackTraceElement[]>> itt = threadMap.entrySet().iterator();
        while(itt.hasNext()){
             
            try {
            	 Entry<Thread, StackTraceElement[]> entry = itt.next();
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
                 
                 String indexFilePath = Constants.BASE_FILE_PATH + name + Constants.FILE_SUBFFIX_INDEX;
                 ObjectToFileUtils.writeObject(status, indexFilePath);
                 
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
            
		}
        
    }
    
    public synchronized void collect(URL url) {
        // 读写统计变量
        int success = url.getParameter(Constants.KEY_SUCCESS, 0);
        int failure = url.getParameter(Constants.KEY_FAILURE, 0);
        int elapsed = url.getParameter(Constants.KEY_ELAPSED, 0);
        String errorCode = url.getParameter(Constants.KEY_ERROR_CODE, "0");
        
        // 初始化原子引用
        
        Statistic update = new Statistic(url, 1);
        String statisticKey = update.toIdentityKey();

        AtomicReference<Statistic> reference = statisticsMap.get(statisticKey);
        if (reference == null) {
            statisticsMap.putIfAbsent(statisticKey, new AtomicReference<Statistic>());
            reference = statisticsMap.get(statisticKey);
        }
        // CompareAndSet并发加入统计数据
        Statistic current;
        do {
            current = reference.get();
            if (current == null) {
            	update.setSuccess(success);
                update.setFailure(failure);
                update.setElapsed(elapsed);
                update.setErrorCode(errorCode);
                update.setMaxElapsed(elapsed);
            } else {
            	update.setSuccess(current.getSuccess() + success);
            	update.setFailure(current.getFailure() + failure);
            	update.setElapsed(current.getElapsed() + elapsed);
            	update.setMaxElapsed(current.getMaxElapsed() < elapsed ? elapsed : current.getMaxElapsed());
            	update.setErrorCode(errorCode);
            }
        } while (! reference.compareAndSet(current, update));        
    }
    
}