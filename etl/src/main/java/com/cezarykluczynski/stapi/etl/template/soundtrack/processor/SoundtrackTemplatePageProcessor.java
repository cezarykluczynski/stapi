package com.cezarykluczynski.stapi.etl.template.soundtrack.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.soundtrack.creation.service.SoundtrackPageFilter;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.etl.template.soundtrack.dto.SoundtrackTemplate;
import com.cezarykluczynski.stapi.etl.util.TitleUtil;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class SoundtrackTemplatePageProcessor implements ItemProcessor<Page, SoundtrackTemplate> {

	private final SoundtrackPageFilter soundtrackPageFilter;

	private final TemplateFinder templateFinder;

	private final PageBindingService pageBindingService;

	private final SoundtrackTemplateCompositeEnrichingProcessor soundtrackTemplateCompositeEnrichingProcessor;

	public SoundtrackTemplatePageProcessor(SoundtrackPageFilter soundtrackPageFilter, TemplateFinder templateFinder,
			PageBindingService pageBindingService, SoundtrackTemplateCompositeEnrichingProcessor soundtrackTemplateCompositeEnrichingProcessor) {
		this.soundtrackPageFilter = soundtrackPageFilter;
		this.templateFinder = templateFinder;
		this.pageBindingService = pageBindingService;
		this.soundtrackTemplateCompositeEnrichingProcessor = soundtrackTemplateCompositeEnrichingProcessor;
	}

	@Override
	public SoundtrackTemplate process(Page item) throws Exception {
		if (soundtrackPageFilter.shouldBeFilteredOut(item)) {
			return null;
		}

		SoundtrackTemplate soundtrackTemplate = new SoundtrackTemplate();
		soundtrackTemplate.setTitle(TitleUtil.getNameFromTitle(item.getTitle()));
		soundtrackTemplate.setPage(pageBindingService.fromPageToPageEntity(item));

		Optional<Template> templateOptional = templateFinder.findTemplate(item, TemplateTitle.SIDEBAR_SOUNDTRACK);

		if (!templateOptional.isPresent()) {
			log.warn("Could not find template {} on page \"{}\"", TemplateTitle.SIDEBAR_SOUNDTRACK, item.getTitle());
			return soundtrackTemplate;
		}

		soundtrackTemplateCompositeEnrichingProcessor.enrich(EnrichablePair.of(templateOptional.get(), soundtrackTemplate));

		return soundtrackTemplate;
	}

}
