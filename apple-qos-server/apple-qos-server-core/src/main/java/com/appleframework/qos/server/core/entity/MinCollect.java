package com.appleframework.qos.server.core.entity;

import java.util.Date;

public class MinCollect extends BaseEntity {

	private static final long serialVersionUID = 4890825905305823561L;
	
	private Integer type;
	private Long collectTime;
	private Date collectDate;

	private Long providerAppId;
	private String providerAppName;
	private Long providerNodeId;
	private String providerAddr;

	private Long consumerAppId;
	private String consumerAppName;
	private Long consumerNodeId;
	private String consumerAddr;
	
	private String method;
	private String service;
	
	private Integer elapsed = 0;
	private Integer success = 0;
	private Integer failure = 0;
	private String errorCode = "0";

	private Integer maxElapsed = 0;
	private Integer avgElapsed = 0;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getCollectTime() {
		return collectTime;
	}

	public void setCollectTime(Long collectTime) {
		this.collectTime = collectTime;
	}

	public Long getProviderAppId() {
		return providerAppId;
	}

	public void setProviderAppId(Long providerAppId) {
		this.providerAppId = providerAppId;
	}

	public String getProviderAppName() {
		return providerAppName;
	}

	public void setProviderAppName(String providerAppName) {
		this.providerAppName = providerAppName;
	}

	public Long getProviderNodeId() {
		return providerNodeId;
	}

	public void setProviderNodeId(Long providerNodeId) {
		this.providerNodeId = providerNodeId;
	}

	public Long getConsumerAppId() {
		return consumerAppId;
	}

	public void setConsumerAppId(Long consumerAppId) {
		this.consumerAppId = consumerAppId;
	}

	public String getConsumerAppName() {
		return consumerAppName;
	}

	public void setConsumerAppName(String consumerAppName) {
		this.consumerAppName = consumerAppName;
	}

	public Long getConsumerNodeId() {
		return consumerNodeId;
	}

	public void setConsumerNodeId(Long consumerNodeId) {
		this.consumerNodeId = consumerNodeId;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getProviderAddr() {
		return providerAddr;
	}

	public void setProviderAddr(String providerAddr) {
		this.providerAddr = providerAddr;
	}

	public String getConsumerAddr() {
		return consumerAddr;
	}

	public void setConsumerAddr(String consumerAddr) {
		this.consumerAddr = consumerAddr;
	}

	public Integer getElapsed() {
		return elapsed;
	}

	public void setElapsed(Integer elapsed) {
		this.elapsed = elapsed;
	}

	public Integer getSuccess() {
		return success;
	}

	public void setSuccess(Integer success) {
		this.success = success;
	}

	public Integer getFailure() {
		return failure;
	}

	public void setFailure(Integer failure) {
		this.failure = failure;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Integer getMaxElapsed() {
		return maxElapsed;
	}

	public void setMaxElapsed(Integer maxElapsed) {
		this.maxElapsed = maxElapsed;
	}

	public Integer getAvgElapsed() {
		return avgElapsed;
	}

	public void setAvgElapsed(Integer avgElapsed) {
		this.avgElapsed = avgElapsed;
	}

	public Date getCollectDate() {
		return collectDate;
	}

	public void setCollectDate(Date collectDate) {
		this.collectDate = collectDate;
	}

}