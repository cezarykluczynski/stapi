package com.cezarykluczynski.stapi.server.common.reader;

public interface Reader<I, O> {

	O read(I input);

}
