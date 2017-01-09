package com.cezarykluczynski.stapi.etl.performer.creation.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.actor.dto.ActorTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader;

import java.util.List;

public interface CategoriesActorTemplateEnrichingProcessor extends ItemEnrichingProcessor<EnrichablePair<List<CategoryHeader>, ActorTemplate>> {
}
