package com.cezarykluczynski.stapi.etl.common.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;

import java.util.List;

public interface ItemWithTemplatePartListEnrichingProcessor<T> extends ItemEnrichingProcessor<EnrichablePair<List<Template.Part>, T>> {
}
