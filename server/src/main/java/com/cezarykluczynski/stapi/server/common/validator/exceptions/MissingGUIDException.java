package com.cezarykluczynski.stapi.server.common.validator.exceptions;

@SuppressWarnings("AbbreviationAsWordInName")
public class MissingGUIDException extends RuntimeException {

	public MissingGUIDException() {
		super("GUID is required");
	}

}
