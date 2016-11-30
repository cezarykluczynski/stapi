package com.cezarykluczynski.stapi.etl.common.processor;

public interface LinkingWorker<T> {

	void link(T source);

}
