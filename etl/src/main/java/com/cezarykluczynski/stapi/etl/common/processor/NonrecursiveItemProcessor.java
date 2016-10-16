package com.cezarykluczynski.stapi.etl.common.processor;

public interface NonrecursiveItemProcessor<I, O> {

	O processNonrecursive(I item);

}
