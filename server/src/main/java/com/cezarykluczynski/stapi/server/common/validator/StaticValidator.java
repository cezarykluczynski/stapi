package com.cezarykluczynski.stapi.server.common.validator;

import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingGUIDException;

public class StaticValidator {

	public static void requireGuid(String guid) {
		if (guid == null) {
			throw new MissingGUIDException();
		}
	}

}
