package com.cezarykluczynski.stapi.etl.common.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;

public interface ItemWithTemplateEnrichingProcessor<T> extends ItemEnrichingProcessor<EnrichablePair<Template, T>> {
}
