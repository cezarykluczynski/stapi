package com.cezarykluczynski.stapi.etl.character.creation.processor;

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor;
import com.cezarykluczynski.stapi.etl.template.individual.processor.IndividualTemplatePageProcessor;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class CharacterProcessor extends CompositeItemProcessor<PageHeader, Character> {

	@Inject
	public CharacterProcessor(PageHeaderProcessor pageHeaderProcessor,
			IndividualTemplatePageProcessor individualTemplatePageProcessor,
			CharacterIndividualTemplateProcessor characterIndividualTemplateProcessor) {
		setDelegates(Lists.newArrayList(pageHeaderProcessor, individualTemplatePageProcessor,
				characterIndividualTemplateProcessor));
	}

}

