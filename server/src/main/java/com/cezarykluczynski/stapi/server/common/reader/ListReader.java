package com.cezarykluczynski.stapi.server.common.reader;

import java.util.List;

public interface ListReader<I, O> {

	List<O> read(I input);

}
