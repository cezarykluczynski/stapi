package com.cezarykluczynski.stapi.etl.common.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;

public interface ItemEnrichingProcessor<I extends EnrichablePair> {

	void enrich(I enrichablePair) throws Exception;

}
