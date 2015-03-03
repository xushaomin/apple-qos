package com.appleframework.qos.server.statistics.model;

import java.io.Serializable;

public class DaySumCode implements Serializable {

	private static final long serialVersionUID = -4641704892701372694L;
	
	private Integer success = 0;
	private Integer failure = 0;

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

}