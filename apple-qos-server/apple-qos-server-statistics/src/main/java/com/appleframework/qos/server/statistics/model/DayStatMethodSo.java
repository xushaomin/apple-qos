package com.appleframework.qos.server.statistics.model;

import java.io.Serializable;
import java.util.Date;

public class DayStatMethodSo implements Serializable {

	private static final long serialVersionUID = -930811591359720119L;

	private Date startDate;
	private Date endDate;
	private String service;
	private String method;
	private String consumerAppName;
	private String providerAppName;

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getConsumerAppName() {
		return consumerAppName;
	}

	public void setConsumerAppName(String consumerAppName) {
		this.consumerAppName = consumerAppName;
	}

	public String getProviderAppName() {
		return providerAppName;
	}

	public void setProviderAppName(String providerAppName) {
		this.providerAppName = providerAppName;
	}

	public DayStatMethodSo(Date startDate, Date endDate, String providerAppName, String consumerAppName, String service,
			String method) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.providerAppName = providerAppName;
		this.consumerAppName = consumerAppName;
		this.service = service;
		this.method = method;
	}
	
	
	public static DayStatMethodSo create(Date startDate, Date endDate, String providerAppName, String consumerAppName, String service, String method) {
		return new DayStatMethodSo(startDate, endDate, providerAppName, consumerAppName, service, method);
	}

}