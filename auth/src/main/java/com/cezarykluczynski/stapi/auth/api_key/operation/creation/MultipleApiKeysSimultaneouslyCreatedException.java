package com.cezarykluczynski.stapi.auth.api_key.operation.creation;

import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException;

class MultipleApiKeysSimultaneouslyCreatedException extends StapiRuntimeException {

	MultipleApiKeysSimultaneouslyCreatedException() {
		super("Multiple API keys created simultaneously");
	}

}
