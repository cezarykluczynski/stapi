package com.cezarykluczynski.stapi.etl.animal.creation.processor;

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor;
import com.cezarykluczynski.stapi.model.animal.entity.Animal;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class AnimalProcessor extends CompositeItemProcessor<PageHeader, Animal> {

	public AnimalProcessor(PageHeaderProcessor pageHeaderProcessor, AnimalPageProcessor animalPageProcessor) {
		setDelegates(Lists.newArrayList(pageHeaderProcessor, animalPageProcessor));
	}

}
