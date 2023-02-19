package com.cezarykluczynski.stapi.etl.common.service.step;

import org.springframework.batch.core.scope.context.ChunkContext;

public interface ChunkLogger {

	void afterChunk(ChunkContext context);

}
