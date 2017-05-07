package com.cezarykluczynski.stapi.server.common.dto;

public class RestEndpointStatisticDTO {

	private String name;

	private long count;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}
}
