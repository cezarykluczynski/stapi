package com.cezarykluczynski.stapi.server.common.reader;

public interface SoapReader<I, O> {

	O read(I input);

}
