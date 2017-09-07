package com.cezarykluczynski.stapi.etl.common.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;

public interface ItemWithTemplatePartEnrichingProcessor<T> extends ItemEnrichingProcessor<EnrichablePair<Template.Part, T>> {
}
