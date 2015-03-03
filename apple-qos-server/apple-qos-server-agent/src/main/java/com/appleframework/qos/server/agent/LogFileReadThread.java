package com.appleframework.qos.server.agent;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.log4j.Logger;

import com.appleframework.qos.collector.core.URL;
import com.appleframework.qos.collector.core.utils.Constants;
import com.appleframework.qos.server.agent.model.ThreadStatus;

public class LogFileReadThread extends Thread {

	private static Logger logger = Logger.getLogger(AgentMonitor.class);

	private RandomAccessFile randomFile = null;
		
	private long offset;
	
	private AgentMonitor agentMonitor;
	
	private boolean done = false;
	
	private AtomicReference<ThreadStatus> reference;

	public RandomAccessFile getRandomFile() {
		return randomFile;
	}

	public AtomicReference<ThreadStatus> getReference() {
		return reference;
	}

	public void setReference(AtomicReference<ThreadStatus> reference) {
		this.reference = reference;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public LogFileReadThread(String name, ThreadStatus status, AgentMonitor agentMonitor)
			throws FileNotFoundException {
		super(name);// 设置线程的名称
		//this.setPriority(pro);// 设置优先级
		this.randomFile = 
				new RandomAccessFile(Constants.BASE_FILE_PATH + name + Constants.FILE_SUBFFIX_LOG, "r");
		this.agentMonitor = agentMonitor;
		
        reference = new AtomicReference<ThreadStatus>();
        
		ThreadStatus current = null;
		do {
			current = reference.get();	        
        } while (! reference.compareAndSet(current, status));  
		
	}

	@Override
	public void run() {
		while (!done) {
			try {
				Thread.sleep(2000);
				ThreadStatus currentStatus = reference.get();
				long currentOffset = reference.get().getOffset();
				offset = currentOffset;
				// 获得变化部分的
				randomFile.seek(offset);
				String tmp = "";
				while ((tmp = randomFile.readLine()) != null) {
					try {
						agentMonitor.collect(URL.valueOf(tmp));
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
					logger.debug(tmp);
				}
				offset = randomFile.length();
				if(currentOffset != offset) {
					long lastReaded = System.currentTimeMillis();
					ThreadStatus updateStatus = new ThreadStatus();
					updateStatus.setName(super.getName());
					updateStatus.setLastReaded(lastReaded);
					updateStatus.setOffset(offset);
					updateStatus.setLastModified(currentStatus.getLastModified());
					do {
						
			        } while (! reference.compareAndSet(currentStatus, updateStatus));  
				}
			} catch (Exception e) {
				 done = true;
				 logger.error(e.getMessage(), e);
			}

		}

	}
}
