package com.cezarykluczynski.stapi.server.common.reader;

public interface FullReader<I, O> { // TODO: I can be String

	// TODO: move cache here
	O readFull(I input);

}
