package com.appleframework.qos.server.core.entity;

import java.io.Serializable;
import java.util.Date;

public class BaseStat implements Serializable {

	private static final long serialVersionUID = 3008940218793374940L;
	
	protected Long id;
	protected Date statDate;

	protected Long failSumNumber = 0L;
	protected Long failSumElapsed = 0L;
	protected Long failMaxElapsed = 0L;
	protected Long failAvgElapsed = 0L;

	protected Long succSumNumber = 0L;
	protected Long succSumElapsed = 0L;
	protected Long succMaxElapsed = 0L;
	protected Long succAvgElapsed = 0L;

	protected Long totalSumElapsed = 0L;
	protected Long totalSumNumber = 0L;
	protected Long totalAvgElapsed = 0L;
	protected Double totalSuccPer = 0D;
	
	protected Date createTime;
	protected Date updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getStatDate() {
		return statDate;
	}

	public void setStatDate(Date statDate) {
		this.statDate = statDate;
	}

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

	public Long getFailAvgElapsed() {
		return failAvgElapsed;
	}

	public void setFailAvgElapsed(Long failAvgElapsed) {
		this.failAvgElapsed = failAvgElapsed;
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

	public Long getSuccAvgElapsed() {
		return succAvgElapsed;
	}

	public void setSuccAvgElapsed(Long succAvgElapsed) {
		this.succAvgElapsed = succAvgElapsed;
	}

	public Long getTotalSumElapsed() {
		return totalSumElapsed;
	}

	public void setTotalSumElapsed(Long totalSumElapsed) {
		this.totalSumElapsed = totalSumElapsed;
	}

	public Long getTotalSumNumber() {
		return totalSumNumber;
	}

	public void setTotalSumNumber(Long totalSumNumber) {
		this.totalSumNumber = totalSumNumber;
	}

	public Double getTotalSuccPer() {
		return totalSuccPer;
	}

	public void setTotalSuccPer(Double totalSuccPer) {
		this.totalSuccPer = totalSuccPer;
	}

	public Long getTotalAvgElapsed() {
		return totalAvgElapsed;
	}

	public void setTotalAvgElapsed(Long totalAvgElapsed) {
		this.totalAvgElapsed = totalAvgElapsed;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}