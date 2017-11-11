package com.cezarykluczynski.stapi.auth.api_key.operation.common;

import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException;

public class ApiKeyException extends StapiRuntimeException {

	public ApiKeyException(String message) {
		super(message);
	}

}
