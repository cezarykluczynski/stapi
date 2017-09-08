package com.cezarykluczynski.stapi.etl.template.individual.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.individual.dto.CharacterTemplate;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class CharacterTemplateMirrorAlternateUniverseEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Page, CharacterTemplate>> {

	private static final String MIRROR = "(mirror)";

	private static final String ALTERNATE_REALITY = "(alternate reality)";

	private final TemplateFinder templateFinder;

	private final CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	@Inject
	public CharacterTemplateMirrorAlternateUniverseEnrichingProcessor(TemplateFinder templateFinder,
			CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor) {
		this.templateFinder = templateFinder;
		this.categoryTitlesExtractingProcessor = categoryTitlesExtractingProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Page, CharacterTemplate> enrichablePair) throws Exception {
		Page page = enrichablePair.getInput();
		CharacterTemplate characterTemplate = enrichablePair.getOutput();
		String pageTitle = page.getTitle();
		List<String> categoryNameList = categoryTitlesExtractingProcessor.process(page.getCategories());

		if (pageTitle.contains(MIRROR)) {
			characterTemplate.setMirror(true);
		}

		if (templateFinder.findTemplate(page, TemplateTitle.MIRROR).isPresent()) {
			characterTemplate.setMirror(true);
		}

		if (categoryNameList.contains(CategoryTitle.MIRROR_UNIVERSE_INHABITANTS)) {
			characterTemplate.setMirror(true);
		}

		if (pageTitle.contains(ALTERNATE_REALITY)) {
			characterTemplate.setAlternateReality(true);
		}

		if (templateFinder.findTemplate(page, TemplateTitle.ALT_REALITY).isPresent()) {
			characterTemplate.setAlternateReality(true);
		}

		if (categoryNameList.stream().anyMatch(categoryName -> categoryName.contains(ALTERNATE_REALITY))) {
			characterTemplate.setAlternateReality(true);
		}
	}

}
