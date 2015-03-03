package com.appleframework.qos.server.monitor.model;

import java.io.Serializable;

public class BaseStatistic implements Serializable {

	private static final long serialVersionUID = 7191286821877188872L;

	protected Long failSumNumber = 0L;
	protected Long failSumElapsed = 0L;
	protected Long failMaxElapsed = 0L;

	protected Long succSumNumber = 0L;
	protected Long succSumElapsed = 0L;
	protected Long succMaxElapsed = 0L;

	public Long getFailSumNumber() {
		return failSumNumber;
	}

	public void setFailSumNumber(Long failSumNumber) {
		this.failSumNumber = failSumNumber;
	}

	public Long getFailSumElapsed() {
		return failSumElapsed;
	}

	public void setFailSumElapsed(Long failSumElapsed) {
		this.failSumElapsed = failSumElapsed;
	}

	public Long getFailMaxElapsed() {
		return failMaxElapsed;
	}

	public void setFailMaxElapsed(Long failMaxElapsed) {
		this.failMaxElapsed = failMaxElapsed;
	}

	public Long getSuccSumNumber() {
		return succSumNumber;
	}

	public void setSuccSumNumber(Long succSumNumber) {
		this.succSumNumber = succSumNumber;
	}

	public Long getSuccSumElapsed() {
		return succSumElapsed;
	}

	public void setSuccSumElapsed(Long succSumElapsed) {
		this.succSumElapsed = succSumElapsed;
	}

	public Long getSuccMaxElapsed() {
		return succMaxElapsed;
	}

	public void setSuccMaxElapsed(Long succMaxElapsed) {
		this.succMaxElapsed = succMaxElapsed;
	}

}