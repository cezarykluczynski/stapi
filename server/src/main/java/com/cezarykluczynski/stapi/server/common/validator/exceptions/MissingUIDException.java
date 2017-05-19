package com.cezarykluczynski.stapi.server.common.validator.exceptions;

@SuppressWarnings("AbbreviationAsWordInName")
public class MissingUIDException extends RuntimeException {

	public MissingUIDException() {
		super("UID is required");
	}

}
