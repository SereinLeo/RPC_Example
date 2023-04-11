package com.wanfeng.common;

import java.io.Serializable;

/**
 * Date: 2023-04-10 0010 22:07
 * Author: WanFeng
 * Description:
 */
public class URL implements Serializable {
	private String hostname;
	private Integer port;

	public URL(String hostname, Integer port) {
		this.hostname = hostname;
		this.port = port;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}
}
