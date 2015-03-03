package com.appleframework.qos.server.statistics.task;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;  
import org.springframework.stereotype.Component;

import com.appleframework.qos.server.statistics.service.DayStatService;


@Component("minStaticsTask")
public class DayStaticsTask {

	@Resource
	private DayStatService dayStatService;
		
	@SuppressWarnings("deprecation")
	@Scheduled(cron = "30 0/1 * * * ?")
    public void jobNowDate() {
		Date date = new Date();
		date.setMonth(0);
		date.setDate(14);
		date.setHours(0);
		date.setSeconds(0);
		date.setMinutes(0);
		this.stat(date);
    }
	
	@SuppressWarnings("deprecation")
	@Scheduled(cron = "0 0 1 * * ?")
    public void jobYesDate() {
		long time = System.currentTimeMillis();
		Date date = new Date(time - 86400000);
		date.setHours(0);
		date.setSeconds(0);
		date.setMinutes(0);
		this.stat(date);	
    }
	
	public void stat(Date date) {
		try {
			dayStatService.app(date);
		} catch (Exception e) {
		}
		
		try {
			dayStatService.code(date);
		} catch (Exception e) {
		}
		
		try {
			dayStatService.node(date);
		} catch (Exception e) {
		}
		
		try {
			dayStatService.method(date);
		} catch (Exception e) {
		}
	}
}