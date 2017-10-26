package com.cezarykluczynski.stapi.etl.template.military_conflict.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.conflict.creation.service.ConflictPageFilter;
import com.cezarykluczynski.stapi.etl.template.military_conflict.dto.MilitaryConflictTemplate;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.etl.util.TitleUtil;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MilitaryConflictTemplatePageProcessor implements ItemProcessor<Page, MilitaryConflictTemplate> {

	private final ConflictPageFilter conflictPageFilter;

	private final PageBindingService pageBindingService;

	private final TemplateFinder templateFinder;

	private final MilitaryConflictTemplatePartsEnrichingProcessor militaryConflictTemplatePartsEnrichingProcessor;

	private final MilitaryConflictTemplateCompositeEnrichingProcessor militaryConflictTemplateCompositeEnrichingProcessor;

	public MilitaryConflictTemplatePageProcessor(ConflictPageFilter conflictPageFilter, PageBindingService pageBindingService,
			TemplateFinder templateFinder, MilitaryConflictTemplatePartsEnrichingProcessor militaryConflictTemplatePartsEnrichingProcessor,
			MilitaryConflictTemplateCompositeEnrichingProcessor militaryConflictTemplateCompositeEnrichingProcessor) {
		this.conflictPageFilter = conflictPageFilter;
		this.pageBindingService = pageBindingService;
		this.templateFinder = templateFinder;
		this.militaryConflictTemplatePartsEnrichingProcessor = militaryConflictTemplatePartsEnrichingProcessor;
		this.militaryConflictTemplateCompositeEnrichingProcessor = militaryConflictTemplateCompositeEnrichingProcessor;
	}

	@Override
	public MilitaryConflictTemplate process(Page item) throws Exception {
		if (conflictPageFilter.shouldBeFilteredOut(item)) {
			return null;
		}

		MilitaryConflictTemplate militaryConflictTemplate = new MilitaryConflictTemplate();
		militaryConflictTemplate.setName(TitleUtil.getNameFromTitle(item.getTitle()));
		militaryConflictTemplate.setPage(pageBindingService.fromPageToPageEntity(item));

		Optional<Template> militaryConflictTemplateOptional = templateFinder.findTemplate(item, TemplateTitle.SIDEBAR_MILITARY_CONFLICT);

		if (militaryConflictTemplateOptional.isPresent()) {
			militaryConflictTemplatePartsEnrichingProcessor
					.enrich(EnrichablePair.of(militaryConflictTemplateOptional.get().getParts(), militaryConflictTemplate));
		}

		militaryConflictTemplateCompositeEnrichingProcessor.enrich(EnrichablePair.of(item, militaryConflictTemplate));

		return militaryConflictTemplate;
	}

}
