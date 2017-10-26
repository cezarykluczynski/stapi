package com.cezarykluczynski.stapi.etl.template.military_conflict.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.common.processor.TemplateTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.template.military_conflict.dto.MilitaryConflictTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.stereotype.Service;

@Service
public class MilitaryConflictTemplateCompositeEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Page, MilitaryConflictTemplate>> {

	private final CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	private final TemplateTitlesExtractingProcessor templateTitlesExtractingProcessor;

	private final MilitaryConflictTemplateCategoriesEnrichingProcessor militaryConflictTemplateCategoriesEnrichingProcessor;

	private final MilitaryConflictTemplateTemplatesEnrichingProcessor militaryConflictTemplateTemplatesEnrichingProcessor;

	public MilitaryConflictTemplateCompositeEnrichingProcessor(CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor,
			TemplateTitlesExtractingProcessor templateTitlesExtractingProcessor,
			MilitaryConflictTemplateCategoriesEnrichingProcessor militaryConflictTemplateCategoriesEnrichingProcessor,
			MilitaryConflictTemplateTemplatesEnrichingProcessor militaryConflictTemplateTemplatesEnrichingProcessor) {
		this.categoryTitlesExtractingProcessor = categoryTitlesExtractingProcessor;
		this.templateTitlesExtractingProcessor = templateTitlesExtractingProcessor;
		this.militaryConflictTemplateCategoriesEnrichingProcessor = militaryConflictTemplateCategoriesEnrichingProcessor;
		this.militaryConflictTemplateTemplatesEnrichingProcessor = militaryConflictTemplateTemplatesEnrichingProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Page, MilitaryConflictTemplate> enrichablePair) throws Exception {
		Page page = enrichablePair.getInput();
		MilitaryConflictTemplate militaryConflictTemplate = enrichablePair.getOutput();

		militaryConflictTemplateCategoriesEnrichingProcessor.enrich(EnrichablePair.of(categoryTitlesExtractingProcessor
				.process(page.getCategories()), militaryConflictTemplate));
		militaryConflictTemplateTemplatesEnrichingProcessor.enrich(EnrichablePair.of(templateTitlesExtractingProcessor
				.process(page.getTemplates()), militaryConflictTemplate));
	}


}
