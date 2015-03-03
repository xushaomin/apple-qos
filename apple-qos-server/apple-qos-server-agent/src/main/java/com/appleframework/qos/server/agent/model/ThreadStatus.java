package com.appleframework.qos.server.agent.model;

import java.io.Serializable;

public class ThreadStatus implements Serializable {

	private static final long serialVersionUID = -8899202017288257556L;

	private String name;
	
	private long offset;

	private long lastModified;

	private long lastReaded;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getOffset() {
		return offset;
	}

	public void setOffset(long offset) {
		this.offset = offset;
	}

	public long getLastModified() {
		return lastModified;
	}

	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

	public long getLastReaded() {
		return lastReaded;
	}

	public void setLastReaded(long lastReaded) {
		this.lastReaded = lastReaded;
	}

	public ThreadStatus() {
		
	}

	public ThreadStatus(String name, long offset, long lastModified,
			long lastReaded) {
		super();
		this.name = name;
		this.offset = offset;
		this.lastModified = lastModified;
		this.lastReaded = lastReaded;
	}

}
