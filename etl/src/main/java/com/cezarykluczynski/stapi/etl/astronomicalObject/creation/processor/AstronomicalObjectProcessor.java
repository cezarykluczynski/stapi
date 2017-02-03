package com.cezarykluczynski.stapi.etl.astronomicalObject.creation.processor;

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor;
import com.cezarykluczynski.stapi.etl.template.planet.processor.PlanetTemplatePageProcessor;
import com.cezarykluczynski.stapi.model.astronomicalObject.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class AstronomicalObjectProcessor extends CompositeItemProcessor<PageHeader, AstronomicalObject> {

	@Inject
	public AstronomicalObjectProcessor(PageHeaderProcessor pageHeaderProcessor, PlanetTemplatePageProcessor planetTemplatePageProcessor,
			PlanetTemplateProcessor planetTemplateProcessor) {
		setDelegates(Lists.newArrayList(pageHeaderProcessor, planetTemplatePageProcessor, planetTemplateProcessor));
	}

}
