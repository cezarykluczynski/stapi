package com.cezarykluczynski.stapi.etl.common.processor;

public interface LinkingWorker<T, B> {

	void link(T source, B baseEntity);

}
