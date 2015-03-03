package com.appleframework.qos.server.monitor.model;

import java.io.Serializable;

public class Collect implements Serializable {

	private static final long serialVersionUID = 7258049125956439697L;

	private String providerName;
	private String consumerName;
	private Integer type;
	private Long collectTime;

	private String method;
	private String service;
	private String providerAddr;
	private String consumerAddr;

	private Integer elapsed = 0;
	private Integer success = 0;
	private Integer failure = 0;

	private Integer maxElapsed = 0;

	private String errorCode;

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

	public Integer getMaxElapsed() {
		return maxElapsed;
	}

	public void setMaxElapsed(Integer maxElapsed) {
		this.maxElapsed = maxElapsed;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getConsumerName() {
		return consumerName;
	}

	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
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

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public String toString() {
		return "Collect [providerName=" + providerName + ", consumerName="
				+ consumerName + ", type=" + type + ", collectTime=" + collectTime
				+ ", method=" + method + ", service="
				+ service + ", providerAddr=" + providerAddr
				+ ", consumerAddr=" + consumerAddr + ", elapsed=" + elapsed
				+ ", success=" + success + ", failure=" + failure
				+ ", maxElapsed=" + maxElapsed + ", errorCode=" + errorCode
				+ "]";
	}

}