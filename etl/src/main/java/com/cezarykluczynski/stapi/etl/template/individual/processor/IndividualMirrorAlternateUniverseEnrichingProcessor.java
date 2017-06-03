package com.cezarykluczynski.stapi.etl.template.individual.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualTemplate;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class IndividualMirrorAlternateUniverseEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Page, IndividualTemplate>> {

	private static final String MIRROR = "(mirror)";

	private static final String ALTERNATE_REALITY = "(alternate reality)";

	private final TemplateFinder templateFinder;

	private final CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	@Inject
	public IndividualMirrorAlternateUniverseEnrichingProcessor(TemplateFinder templateFinder,
			CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor) {
		this.templateFinder = templateFinder;
		this.categoryTitlesExtractingProcessor = categoryTitlesExtractingProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Page, IndividualTemplate> enrichablePair) throws Exception {
		Page page = enrichablePair.getInput();
		IndividualTemplate individualTemplate = enrichablePair.getOutput();
		String pageTitle = page.getTitle();
		List<String> categoryNameList = categoryTitlesExtractingProcessor.process(page.getCategories());

		if (pageTitle.contains(MIRROR)) {
			individualTemplate.setMirror(true);
		}

		if (templateFinder.findTemplate(page, TemplateTitle.MIRROR).isPresent()) {
			individualTemplate.setMirror(true);
		}

		if (categoryNameList.contains(CategoryTitle.MIRROR_UNIVERSE_INHABITANTS)) {
			individualTemplate.setMirror(true);
		}

		if (pageTitle.contains(ALTERNATE_REALITY)) {
			individualTemplate.setAlternateReality(true);
		}

		if (templateFinder.findTemplate(page, TemplateTitle.ALT_REALITY).isPresent()) {
			individualTemplate.setAlternateReality(true);
		}

		if (categoryNameList.stream().anyMatch(categoryName -> categoryName.contains(ALTERNATE_REALITY))) {
			individualTemplate.setAlternateReality(true);
		}
	}

}
