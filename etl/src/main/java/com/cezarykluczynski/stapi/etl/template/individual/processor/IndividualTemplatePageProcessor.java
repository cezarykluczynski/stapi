package com.cezarykluczynski.stapi.etl.template.individual.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.template.characterbox.processor.CharacterboxIndividualTemplateEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualTemplate;
import com.cezarykluczynski.stapi.etl.template.individual.service.IndividualTemplateFilter;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.etl.util.TitleUtil;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Optional;

@Service
@Slf4j
public class IndividualTemplatePageProcessor implements ItemProcessor<Page, IndividualTemplate> {

	private final IndividualTemplateFilter individualTemplateFilter;

	private final PageBindingService pageBindingService;

	private final TemplateFinder templateFinder;

	private final IndividualTemplateCompositeEnrichingProcessor individualTemplateCompositeEnrichingProcessor;

	private final CharacterboxIndividualTemplateEnrichingProcessor characterboxIndividualTemplateEnrichingProcessor;

	@Inject
	public IndividualTemplatePageProcessor(IndividualTemplateFilter individualTemplateFilter, PageBindingService pageBindingService,
			TemplateFinder templateFinder, IndividualTemplateCompositeEnrichingProcessor individualTemplateCompositeEnrichingProcessor,
			CharacterboxIndividualTemplateEnrichingProcessor characterboxIndividualTemplateEnrichingProcessor) {
		this.individualTemplateFilter = individualTemplateFilter;
		this.pageBindingService = pageBindingService;
		this.templateFinder = templateFinder;
		this.individualTemplateCompositeEnrichingProcessor = individualTemplateCompositeEnrichingProcessor;
		this.characterboxIndividualTemplateEnrichingProcessor = characterboxIndividualTemplateEnrichingProcessor;
	}

	@Override
	public IndividualTemplate process(Page item) throws Exception {
		if (individualTemplateFilter.shouldBeFilteredOut(item)) {
			return null;
		}

		IndividualTemplate individualTemplate = new IndividualTemplate();
		individualTemplate.setName(TitleUtil.getNameFromTitle(item.getTitle()));
		individualTemplate.setPage(pageBindingService.fromPageToPageEntity(item));
		individualTemplate.setProductOfRedirect(!item.getRedirectPath().isEmpty());

		Optional<Template> sidebarIndividualTemplateOptional = templateFinder.findTemplate(item, TemplateTitle.SIDEBAR_INDIVIDUAL);

		if (!sidebarIndividualTemplateOptional.isPresent()) {
			return individualTemplate;
		}

		individualTemplateCompositeEnrichingProcessor.enrich(EnrichablePair.of(item, individualTemplate));

		Optional<Template> memoryBetaTemplateOptional = templateFinder.findTemplate(item, TemplateTitle.MBETA);

		if (memoryBetaTemplateOptional.isPresent()) {
			characterboxIndividualTemplateEnrichingProcessor.enrich(EnrichablePair.of(memoryBetaTemplateOptional.get(), individualTemplate));
		}

		return individualTemplate;
	}

}
