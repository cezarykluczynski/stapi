package com.cezarykluczynski.stapi.etl.template.military_conflict.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.military_conflict.dto.MilitaryConflictTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MilitaryConflictTemplateCompositeEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Page, MilitaryConflictTemplate>> {

	private final CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	private final MilitaryConflictTemplateCategoriesEnrichingProcessor militaryConflictTemplateCategoriesEnrichingProcessor;

	private final MilitaryConflictTemplateTemplatesEnrichingProcessor militaryConflictTemplateTemplatesEnrichingProcessor;

	@Inject
	public MilitaryConflictTemplateCompositeEnrichingProcessor(CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor,
			MilitaryConflictTemplateCategoriesEnrichingProcessor militaryConflictTemplateCategoriesEnrichingProcessor,
			MilitaryConflictTemplateTemplatesEnrichingProcessor militaryConflictTemplateTemplatesEnrichingProcessor) {
		this.categoryTitlesExtractingProcessor = categoryTitlesExtractingProcessor;
		this.militaryConflictTemplateCategoriesEnrichingProcessor = militaryConflictTemplateCategoriesEnrichingProcessor;
		this.militaryConflictTemplateTemplatesEnrichingProcessor = militaryConflictTemplateTemplatesEnrichingProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Page, MilitaryConflictTemplate> enrichablePair) throws Exception {
		Page page = enrichablePair.getInput();
		MilitaryConflictTemplate militaryConflictTemplate = enrichablePair.getOutput();

		militaryConflictTemplateCategoriesEnrichingProcessor.enrich(EnrichablePair.of(categoryTitlesExtractingProcessor
				.process(page.getCategories()), militaryConflictTemplate));
		militaryConflictTemplateTemplatesEnrichingProcessor.enrich(EnrichablePair.of(getTemplateTitles(page), militaryConflictTemplate));
	}

	private List<String> getTemplateTitles(Page page) {
		return page.getTemplates().stream().map(Template::getTitle).collect(Collectors.toList());
	}

}
