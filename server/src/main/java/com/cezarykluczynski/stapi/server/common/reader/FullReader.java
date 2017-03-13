package com.cezarykluczynski.stapi.server.common.reader;

public interface FullReader<I, O> {

	O readFull(I input);

}
