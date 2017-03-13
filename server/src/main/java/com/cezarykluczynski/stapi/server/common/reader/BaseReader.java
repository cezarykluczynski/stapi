package com.cezarykluczynski.stapi.server.common.reader;

public interface BaseReader<I, O> {

	O readBase(I input);

}
