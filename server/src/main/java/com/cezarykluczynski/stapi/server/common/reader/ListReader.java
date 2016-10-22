package com.cezarykluczynski.stapi.server.common.reader;

import java.util.List;

public interface ListReader<I, O> {

	List<O> search(I input);

	List<O> getAll();

}
