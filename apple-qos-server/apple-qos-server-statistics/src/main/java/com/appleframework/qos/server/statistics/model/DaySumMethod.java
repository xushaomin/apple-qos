package com.appleframework.qos.server.statistics.model;

import java.io.Serializable;

public class DaySumMethod implements Serializable {

	private static final long serialVersionUID = -930811591359720119L;
	
	private Integer elapsed = 0;
	private Integer success = 0;
	private Integer failure = 0;
	private Integer maxElapsed = 0;

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

}