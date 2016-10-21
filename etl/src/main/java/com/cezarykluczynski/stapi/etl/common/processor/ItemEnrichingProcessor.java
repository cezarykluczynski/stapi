package com.cezarykluczynski.stapi.etl.common.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;

public interface ItemEnrichingProcessor<I extends EnrichablePair<?, O>, O> {

	void enrich(I enrichablePair);

}
