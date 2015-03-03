package com.appleframework.qos.server.core.entity;

public class DayStatApp extends BaseStat {

	private static final long serialVersionUID = 6841604318045231651L;

	private Long providerAppId;
	private String providerAppName;

	private Long consumerAppId;
	private String consumerAppName;

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
	
}