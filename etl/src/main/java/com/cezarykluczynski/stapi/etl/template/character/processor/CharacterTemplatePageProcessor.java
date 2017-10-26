package com.cezarykluczynski.stapi.etl.template.character.processor;

import com.cezarykluczynski.stapi.etl.character.creation.service.CharacterPageFilter;
import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.template.character.dto.CharacterTemplate;
import com.cezarykluczynski.stapi.etl.util.TitleUtil;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CharacterTemplatePageProcessor implements ItemProcessor<Page, CharacterTemplate> {

	private final CharacterPageFilter characterPageFilter;

	private final PageBindingService pageBindingService;

	private final CharacterTemplateCompositeEnrichingProcessor characterTemplateCompositeEnrichingProcessor;

	public CharacterTemplatePageProcessor(CharacterPageFilter characterPageFilter, PageBindingService pageBindingService,
			CharacterTemplateCompositeEnrichingProcessor characterTemplateCompositeEnrichingProcessor) {
		this.characterPageFilter = characterPageFilter;
		this.pageBindingService = pageBindingService;
		this.characterTemplateCompositeEnrichingProcessor = characterTemplateCompositeEnrichingProcessor;
	}

	@Override
	public CharacterTemplate process(Page item) throws Exception {
		if (characterPageFilter.shouldBeFilteredOut(item)) {
			return null;
		}

		CharacterTemplate characterTemplate = new CharacterTemplate();
		characterTemplate.setName(TitleUtil.getNameFromTitle(item.getTitle()));
		characterTemplate.setPage(pageBindingService.fromPageToPageEntity(item));
		characterTemplateCompositeEnrichingProcessor.enrich(EnrichablePair.of(item, characterTemplate));

		return characterTemplate;
	}

}
