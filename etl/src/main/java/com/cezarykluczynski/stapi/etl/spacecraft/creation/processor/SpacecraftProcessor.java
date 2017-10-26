package com.cezarykluczynski.stapi.etl.spacecraft.creation.processor;

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor;
import com.cezarykluczynski.stapi.etl.template.starship.processor.StarshipTemplatePageProcessor;
import com.cezarykluczynski.stapi.model.spacecraft.entity.Spacecraft;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class SpacecraftProcessor extends CompositeItemProcessor<PageHeader, Spacecraft> {

	public SpacecraftProcessor(PageHeaderProcessor pageHeaderProcessor, StarshipTemplatePageProcessor starshipTemplatePageProcessor,
			StarshipTemplateProcessor starshipClassTemplateProcessor) {
		setDelegates(Lists.newArrayList(pageHeaderProcessor, starshipTemplatePageProcessor, starshipClassTemplateProcessor));
	}

}
