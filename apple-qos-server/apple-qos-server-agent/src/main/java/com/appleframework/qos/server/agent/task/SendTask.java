package com.appleframework.qos.server.agent.task;


import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.appleframework.qos.server.agent.AgentMonitor;

@Component("sendTask")
public class SendTask {

	//private static Logger logger = Logger.getLogger(SendTask.class);

	@Resource
	private AgentMonitor agentMonitor;

	@Scheduled(cron = "0 0/1 * * * ?")
	public void task() {
		/*logger.info("--->send task time: " + 
				DateFormatUtil.toString(new Date(), DateFormatUtil.pattern19));*/
		
		agentMonitor.send();
		
		//logger.info("--->total count =: " + MonitorSendService.count);
	}

}