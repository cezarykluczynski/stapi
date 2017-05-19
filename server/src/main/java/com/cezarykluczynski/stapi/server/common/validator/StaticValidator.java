package com.cezarykluczynski.stapi.server.common.validator;

import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException;

public class StaticValidator {

	public static void requireUid(String uid) {
		if (uid == null) {
			throw new MissingUIDException();
		}
	}

}
