package com.cezarykluczynski.stapi.etl.species.creation.processor;

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor;
import com.cezarykluczynski.stapi.etl.template.species.processor.SpeciesTemplatePageProcessor;
import com.cezarykluczynski.stapi.model.species.entity.Species;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class SpeciesProcessor extends CompositeItemProcessor<PageHeader, Species> {

	@Inject
	public SpeciesProcessor(PageHeaderProcessor pageHeaderProcessor, SpeciesTemplatePageProcessor speciesTemplatePageProcessor,
			SpeciesTemplateProcessor speciesTemplateProcessor) {
		setDelegates(Lists.newArrayList(pageHeaderProcessor, speciesTemplatePageProcessor, speciesTemplateProcessor));
	}

}
