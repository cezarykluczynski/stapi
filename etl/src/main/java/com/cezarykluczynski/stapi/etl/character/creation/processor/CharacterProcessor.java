package com.cezarykluczynski.stapi.etl.character.creation.processor;

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor;
import com.cezarykluczynski.stapi.etl.template.character.processor.CharacterTemplatePageProcessor;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class CharacterProcessor extends CompositeItemProcessor<PageHeader, Character> {

	public CharacterProcessor(PageHeaderProcessor pageHeaderProcessor, CharacterTemplatePageProcessor characterTemplatePageProcessor,
			CharacterTemplateProcessor characterTemplateProcessor) {
		setDelegates(Lists.newArrayList(pageHeaderProcessor, characterTemplatePageProcessor,
				characterTemplateProcessor));
	}

}
