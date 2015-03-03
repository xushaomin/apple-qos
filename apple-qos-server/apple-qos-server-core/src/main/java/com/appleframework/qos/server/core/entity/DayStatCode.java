package com.appleframework.qos.server.core.entity;

import java.util.Date;

public class DayStatCode extends BaseEntity {

	private static final long serialVersionUID = 2394499059072673642L;

	private Date statDate;

	private Long providerAppId;
	private String providerAppName;

	private Long consumerAppId;
	private String consumerAppName;

	private String errorCode = "0";
	private Long totalSumNumber = 0L;
	//private Double totalSumPer = 0D;

	public Date getStatDate() {
		return statDate;
	}

	public void setStatDate(Date statDate) {
		this.statDate = statDate;
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

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/*public Double getTotalSumPer() {
		return totalSumPer;
	}

	public void setTotalSumPer(Double totalSumPer) {
		this.totalSumPer = totalSumPer;
	}*/

}