package com.appleframework.qos.server.monitor.receiver;

import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appleframework.config.core.PropertyConfigurer;
import com.appleframework.jms.kafka.consumer.ObjectMessageConsumer;
import com.appleframework.qos.collector.core.Statistic;
import com.appleframework.qos.collector.core.URL;
import com.appleframework.qos.collector.core.utils.Constants;
import com.appleframework.qos.collector.core.utils.DateFormatUtils;
import com.appleframework.qos.collector.core.utils.NetUtils;
import com.appleframework.qos.server.core.entity.MinStat;
import com.appleframework.qos.server.monitor.RegistryContainer;
import com.appleframework.qos.server.monitor.model.Collect;
import com.appleframework.qos.server.monitor.service.MinCollectService;
import com.appleframework.qos.server.monitor.service.MinStatService;

/**
 * MonitorMessageReceiver
 * 
 * @author cruise.xu
 */
public class MonitorMessageReceiver extends ObjectMessageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(MonitorMessageReceiver.class);
    
    private static final String POISON_PROTOCOL = "poison";
    
    private static final String KEY_MINUTE_STATISTIC = "monitor.minute.statistic";
    
    private Thread saveThread;
    private Thread countThread;
    
    private BlockingQueue<URL> saveQueue;
    private BlockingQueue<URL> countQueue;
        
    private volatile boolean running = true;
        
    private RegistryContainer registryContainer = RegistryContainer.getInstance();
    
    private MinCollectService minCollectService;
    
    private MinStatService minStatService;

    public void setMinCollectService(MinCollectService minCollectService) {
		this.minCollectService = minCollectService;
	}

	public void setMinStatService(MinStatService minStatService) {
		this.minStatService = minStatService;
	}
    
	public void init() {
    	super.init();
    	saveQueue = new LinkedBlockingQueue<URL>(100000);
    	saveThread = new Thread(new Runnable() {
            public void run() {
                while (running) {
                    try {
                        save(); // 记录统计日志
                    } catch (Throwable t) { // 防御性容错
                        logger.error("Unexpected error occur at write stat log, cause: " + t.getMessage(), t);
                        try {
                            Thread.sleep(1000); // 失败延迟
                        } catch (Throwable t2) {
                        }
                    }
                }
            }
        });
    	saveThread.setDaemon(true);
    	saveThread.setName("GscAsyncSaveThread");
    	saveThread.start();
    	
    	countQueue = new LinkedBlockingQueue<URL>(100000);
    	countThread = new Thread(new Runnable() {
            public void run() {
                while (running) {
                    try {
                        count(); // 记录统计日志
                    } catch (Throwable t) { // 防御性容错
                        logger.error("Unexpected error occur at write stat log, cause: " + t.getMessage(), t);
                        try {
                            Thread.sleep(1000); // 失败延迟
                        } catch (Throwable t2) {
                        }
                    }
                }
            }
        });
    	countThread.setDaemon(true);
    	countThread.setName("GscAsyncCountThread");
    	countThread.start();
    }

    public void close() {
        try {
            running = false;
            saveQueue.offer(new URL(POISON_PROTOCOL, NetUtils.LOCALHOST, 0));
            countQueue.offer(new URL(POISON_PROTOCOL, NetUtils.LOCALHOST, 0));
        } catch (Throwable t) {
            logger.warn(t.getMessage(), t);
        }
    }
    
    private void save() throws Exception {
        URL statistics = saveQueue.take();
        logger.info(statistics.toString());
        if (POISON_PROTOCOL.equals(statistics.getProtocol())) {
            return;
        }

        String timestamp = statistics.getParameter(Constants.KEY_TIMESTAMP);
        String service   = statistics.getParameter(Constants.KEY_INTERFACE);
        
        String consumerName = statistics.getParameter(Constants.KEY_CONSUMER_APP);
        String providerName = statistics.getParameter(Constants.KEY_PROVIDER_APP);
        String consumerAddr = statistics.getParameter(Constants.KEY_CONSUMER_ADD);
        String providerAddr = statistics.getParameter(Constants.KEY_PROVIDER_ADD);
        
        int type = statistics.getParameter(Constants.KEY_COLLECT_TYPE, Constants.COLLECT_TYPE_LOCAL);
        
        Collect collect = new Collect();
        collect.setService(service);
        collect.setMethod(statistics.getParameter(Constants.KEY_METHOD));
        collect.setCollectTime(Long.parseLong(timestamp));
        collect.setType(type);
        
        try {
            if (type == Constants.COLLECT_TYPE_PROVIDER) {
            	if(null == consumerName)
            		consumerName = registryContainer.getConsumerApplicationByService(service);
                if(null == providerName)
                	providerName = registryContainer.getProviderApplicationByService(service);
                
                consumerAddr = statistics.getParameter(Constants.KEY_CONSUMER_ADD).replace("-", ":");
                providerAddr = statistics.getHost();
            } else if (type == Constants.COLLECT_TYPE_CONSUMER) {
            	if(null == consumerName)
            		consumerName = registryContainer.getConsumerApplicationByService(service);
                if(null == providerName)
                	providerName = registryContainer.getProviderApplicationByService(service);
                
            	providerAddr = statistics.getParameter(Constants.KEY_PROVIDER_ADD).replace("-", ":");
                consumerAddr = statistics.getHost();
            } else {
            	if(null == providerName || providerName.trim().equals("null"))
            		providerName = consumerName;
                consumerAddr = statistics.getHost();
                providerAddr = consumerAddr;
            }
            collect.setConsumerAddr(consumerAddr);
            collect.setProviderAddr(providerAddr);
            
            collect.setConsumerName(consumerName);
            collect.setProviderName(providerName);
            
            collect.setErrorCode(statistics.getParameter(Constants.KEY_ERROR_CODE));
            collect.setElapsed(statistics.getParameter(Constants.KEY_ELAPSED, 0));
            collect.setFailure(statistics.getParameter(Constants.KEY_FAILURE, 0));
            collect.setSuccess(statistics.getParameter(Constants.KEY_SUCCESS, 0));
            collect.setMaxElapsed(statistics.getParameter(Constants.KEY_MAX_ELAPSED, 0));
			
            minCollectService.save(collect);
            
            if (logger.isInfoEnabled()) {
            	logger.info(timestamp + "====collect=====save================"+collect.toString());
            }
        } catch (Throwable t) {
            logger.error(t.getMessage(), t);
        }
           
    }
    
    
    //count
	private void count() throws Exception {
        URL url = countQueue.take();
        logger.info(url.toString());
        if (POISON_PROTOCOL.equals(url.getProtocol())) {
            return;
        }
        
        // 读写统计变量
        int success = url.getParameter(Constants.KEY_SUCCESS, 0);
        int failure = url.getParameter(Constants.KEY_FAILURE, 0);
        int elapsed = url.getParameter(Constants.KEY_ELAPSED, 0);
        int maxElapsed = url.getParameter(Constants.KEY_MAX_ELAPSED, 0);
        
        String timestamp = url.getParameter(Constants.KEY_TIMESTAMP);
        if(null == timestamp)
        	return;
        
        int minute = PropertyConfigurer.getInteger(KEY_MINUTE_STATISTIC, 5);
        // 初始化原子引用
        Statistic statistic = new Statistic(url, minute);
        
        long succMaxElapsed  = 0, failMaxElapsed = 0;
    	long succSumElapsed  = 0, failSumElapsed = 0;
    	long succSumNumber   = 0, failSumNumber  = 0;
    	long succAvgElapsed  = 0, failAvgElapsed = 0;
    	long totalSumElapsed = 0, totalSumNumber = 0, totalAvgElapsed = 0;
    	double totalSuccPer = 0.0;
    	
        MinStat oldStat = minStatService.getByMd5(statistic.toMD5Key());
        if(null == oldStat) {
        	String service   = url.getParameter(Constants.KEY_INTERFACE);
        	String consumerName = url.getParameter(Constants.KEY_CONSUMER_APP);
            String providerName = url.getParameter(Constants.KEY_PROVIDER_APP);
                        
            int type = url.getParameter(Constants.KEY_COLLECT_TYPE, Constants.COLLECT_TYPE_CONSUMER);
            
            if (type == Constants.COLLECT_TYPE_PROVIDER) {
            	if(null == consumerName)
            		consumerName = registryContainer.getConsumerApplicationByService(service);
                if(null == providerName)
                	providerName = registryContainer.getProviderApplicationByService(service);
                
            } else if (type == Constants.COLLECT_TYPE_CONSUMER) {
            	if(null == consumerName)
            		consumerName = registryContainer.getConsumerApplicationByService(service);
                if(null == providerName)
                	providerName = registryContainer.getProviderApplicationByService(service);
                
            } else {
            	if(null == providerName || providerName.trim().equals("null"))
            		providerName = consumerName;
            }
        	
        	if(success > 0) {
            	succMaxElapsed = maxElapsed;
            	succSumElapsed = elapsed;
            	succSumNumber  = success;
            }
            else {
            	failMaxElapsed = maxElapsed;
            	failSumElapsed = elapsed;
            	failSumNumber  = failure;
            }
        	
        	if(succSumNumber != 0)
        		succAvgElapsed = succSumElapsed / succSumNumber;
        	if(failSumNumber != 0)
        		failAvgElapsed = failSumElapsed / failSumNumber;
        	totalSumElapsed = succSumElapsed + failSumElapsed;
        	totalSumNumber = succSumNumber + failSumNumber;
        	
        	if(totalSumNumber != 0) {
        		totalSuccPer = (double)succSumNumber / (double)totalSumNumber;
        		totalAvgElapsed = totalSumElapsed / totalSumNumber;
        	}
        	
        	String st = statistic.getTimestamp();
        	Date stDate = DateFormatUtils.toDate(st, DateFormatUtils.pattern12);
            
            MinStat stat = new MinStat();
            
            stat.setConsumerAppName(consumerName);
            stat.setProviderAppName(providerName);
                        
            stat.setStatTime(Long.parseLong(st));
            stat.setStatDate(stDate);
            stat.setService(statistic.getService());
            stat.setMethod(statistic.getMethod());
            
            stat.setSuccAvgElapsed(succAvgElapsed);
            stat.setSuccMaxElapsed(succMaxElapsed);
            stat.setSuccSumElapsed(succSumElapsed);
            stat.setSuccSumNumber(succSumNumber);
            stat.setFailAvgElapsed(failAvgElapsed);
            stat.setFailMaxElapsed(failMaxElapsed);
            stat.setFailSumElapsed(failSumElapsed);
            stat.setFailSumNumber(failSumNumber);
            stat.setTotalSuccPer(totalSuccPer);
            stat.setTotalSumElapsed(totalSumElapsed);
            stat.setTotalAvgElapsed(totalAvgElapsed);
            stat.setTotalSumNumber(totalSumNumber);
            stat.setMd5(statistic.toMD5Key());
            
            minStatService.save(stat);
            
            if (logger.isInfoEnabled()) {
            	logger.info(timestamp + "====stat=====save================"+stat.toString());
            }
        }
        else {
        	succMaxElapsed = oldStat.getSuccMaxElapsed();
        	failMaxElapsed = oldStat.getFailMaxElapsed();
        	
         	succSumElapsed = oldStat.getSuccSumElapsed();
         	failSumElapsed = oldStat.getFailSumElapsed();
         	
         	succSumNumber = oldStat.getSuccSumNumber();
         	failSumNumber = oldStat.getFailSumNumber();
         	
         	if(success > 0) {
        		if(maxElapsed > succMaxElapsed)
        			succMaxElapsed = maxElapsed;
            	succSumElapsed = elapsed + succSumElapsed;
            	succSumNumber  = success + succSumNumber;
            }
            else {
            	if(maxElapsed > failMaxElapsed)
            		failMaxElapsed = maxElapsed;
            	
            	failSumElapsed = elapsed + failSumElapsed;
            	failSumNumber  = failure + failSumNumber;
            }
         	
         	if(succSumNumber != 0)
        		succAvgElapsed = succSumElapsed / succSumNumber;
        	if(failSumNumber != 0)
        		failAvgElapsed = failSumElapsed / failSumNumber;
        	totalSumElapsed = succSumElapsed + failSumElapsed;
        	totalSumNumber = succSumNumber + failSumNumber;
        	
        	if(totalSumNumber != 0) {
        		totalSuccPer = (double)succSumNumber / (double)totalSumNumber;
        		totalAvgElapsed = totalSumElapsed / totalSumNumber;
        	}
         	            
        	oldStat.setSuccAvgElapsed(succAvgElapsed);
        	oldStat.setSuccMaxElapsed(succMaxElapsed);
        	oldStat.setSuccSumElapsed(succSumElapsed);
        	oldStat.setSuccSumNumber(succSumNumber);
        	oldStat.setFailAvgElapsed(failAvgElapsed);
        	oldStat.setFailMaxElapsed(failMaxElapsed);
        	oldStat.setFailSumElapsed(failSumElapsed);
        	oldStat.setFailSumNumber(failSumNumber);
        	oldStat.setTotalSuccPer(totalSuccPer);
        	oldStat.setTotalSumElapsed(totalSumElapsed);
        	oldStat.setTotalAvgElapsed(totalAvgElapsed);
        	oldStat.setTotalSumNumber(totalSumNumber); 
        	
        	minStatService.update(oldStat);
            
            if (logger.isInfoEnabled()) {
            	logger.info(timestamp + "====stat=====update================"+oldStat.toString());
            }
        }    	
        
        
    }
    
    @Override
	public void processMessage() {
		URL statistics = (URL) message;
		saveQueue.offer(statistics);
		countQueue.offer(statistics);
	}

}