package com.appleframework.qos.server.core.entity;

import java.util.Date;

public class NodeInfo extends BaseEntity {

	private static final long serialVersionUID = -7543855169549300923L;

	private String ip;
	private String host;

	private Short state;
	private Short isAuto;
	private Integer disorder;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Short getState() {
		return state;
	}

	public void setState(Short state) {
		this.state = state;
	}

	public Integer getDisorder() {
		return disorder;
	}

	public void setDisorder(Integer disorder) {
		this.disorder = disorder;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public Short getIsAuto() {
		return isAuto;
	}

	public void setIsAuto(Short isAuto) {
		this.isAuto = isAuto;
	}

}
