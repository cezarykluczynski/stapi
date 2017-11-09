package com.cezarykluczynski.stapi.auth.api_key.operation.removal;

import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException;

class ApiKeyRemovalException extends StapiRuntimeException {

	ApiKeyRemovalException(String message) {
		super(message);
	}

}
