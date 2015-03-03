package com.appleframework.qos.server.agent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.log4j.Logger;

import com.appleframework.qos.collector.core.utils.Constants;
import com.appleframework.qos.server.agent.model.ThreadStatus;
import com.appleframework.qos.server.agent.utils.ObjectToFileUtils;

public class LogFileReadStarter {
	
	private static Logger logger = Logger.getLogger(AgentMonitor.class);
	
	private ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);	
	
	private AgentMonitor agentMonitor;
	
	private String baseFilePath;
	
	public static ConcurrentMap<String, AtomicReference<String>> threadMap 
			= new ConcurrentHashMap<String, AtomicReference<String>>();
	
	public void setBaseFilePath(String baseFilePath) {
		this.baseFilePath = baseFilePath;
	}

	public ScheduledExecutorService getExec() {
		return exec;
	}

	public void setExec(ScheduledExecutorService exec) {
		this.exec = exec;
	}

	public void setAgentMonitor(AgentMonitor agentMonitor) {
		this.agentMonitor = agentMonitor;
	}

	/**
	 * 实时输出日志信息
	 * 
	 * @param logFile
	 *            日志文件
	 * @throws FileNotFoundException 
	 * @throws IOException
	 */
	public void realtimeReadLog() throws FileNotFoundException {
				
		// 启动一个线程每10秒钟读取新增的日志信息
		exec.scheduleWithFixedDelay(new Runnable() {
			public void run() {
				try {
					if(null != baseFilePath)
						Constants.BASE_FILE_PATH = baseFilePath;
					final File logPath = new File(Constants.BASE_FILE_PATH);
					File list[] = logPath.listFiles();

					long lastReaded = System.currentTimeMillis();
					
					for (int i = 0; i < list.length; i++)
						if (list[i].isFile()) {
							File file = list[i];
							String fileName = file.getName().trim();
							long lastModified = file.lastModified();
							String name = null;
							//System.out.println("fileName = " + fileName);
							//System.out.println("lastModified = " + lastModified);
							
							int logFileIndex = fileName.indexOf(Constants.FILE_SUBFFIX_LOG);
							long offset = 0L;
							String indexFilePath = null;
							ThreadStatus status = null;
							if(logFileIndex > -1) {
								name = fileName.substring(0, logFileIndex);
								
								AtomicReference<String> reference = threadMap.get(name);
								if (reference == null) {
						            threadMap.putIfAbsent(name, new AtomicReference<String>());
						            reference = threadMap.get(name);
						        }
					            String currentName = reference.get();		        
						        
								if(null != currentName && currentName.equals(name)) {
									continue;
								}
								
								indexFilePath = Constants.BASE_FILE_PATH + name + ".index";
								File indexFile = new File(indexFilePath);
								if(!indexFile.exists()) {
									if(indexFile.createNewFile()) {
										status = new ThreadStatus(name, offset, lastModified, lastReaded);
										ObjectToFileUtils.writeObject(status, indexFilePath);
									}
								}
								else {
									status = (ThreadStatus)ObjectToFileUtils.readObject(indexFilePath);
									status.setLastModified(lastModified);
									status.setLastReaded(lastReaded);
								}
								
								LogFileReadThread thread = new LogFileReadThread(name, status, agentMonitor);
								thread.start();
								
						        // CompareAndSet并发加入统计数据
						        String current;
						        
						        do {
						            current = reference.get();
						        } while (! reference.compareAndSet(current, name));
						        
						        logger.info("-------------------进程开始：" + name);
								//threadSet.add(name);
								
							}
							else {
								continue;
							}

						}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}, 0, 600, TimeUnit.SECONDS);
		
	}
	
	public void init() throws Exception {
		this.realtimeReadLog();
	}
	
}
