package com.appleframework.qos.server.core.entity;

public class DayStatNode extends BaseStat {

	private static final long serialVersionUID = -8255049505353351561L;

	private Long providerAppId;
	private String providerAppName;

	private Long consumerAppId;
	private String consumerAppName;
	
	private String providerNodeIp;
	private Long providerNodeId;

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

	public Long getTotalSumNumber() {
		return totalSumNumber;
	}

	public void setTotalSumNumber(Long totalSumNumber) {
		this.totalSumNumber = totalSumNumber;
	}

	public String getProviderNodeIp() {
		return providerNodeIp;
	}

	public void setProviderNodeIp(String providerNodeIp) {
		this.providerNodeIp = providerNodeIp;
	}

	public Long getProviderNodeId() {
		return providerNodeId;
	}

	public void setProviderNodeId(Long providerNodeId) {
		this.providerNodeId = providerNodeId;
	}
	
}