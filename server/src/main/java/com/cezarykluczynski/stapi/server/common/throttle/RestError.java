package com.cezarykluczynski.stapi.server.common.throttle;

public class RestError {

	private String code;

	private String description;

	RestError(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
}
