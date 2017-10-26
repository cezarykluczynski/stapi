package com.cezarykluczynski.stapi.etl.template.character.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.character.dto.CharacterTemplate;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterTemplateFlagsEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Page, CharacterTemplate>> {

	private static final String MIRROR = "(mirror)";
	private static final String ALTERNATE_REALITY = "(alternate reality)";
	private static final List<String> FICTIONAL_CHARACTERS_CATEGORIES = Lists.newArrayList(CategoryTitle.FICTIONAL_CHARACTERS,
			CategoryTitle.THE_DIXON_HILL_SERIES_CHARACTERS, CategoryTitle.SHAKESPEARE_CHARACTERS);
	private static final List<String> HOLOGRAMS_CATEGORIES = Lists.newArrayList(CategoryTitle.HOLOGRAMS, CategoryTitle.HOLOGRAPHIC_DUPLICATES,
			CategoryTitle.THE_ADVENTURES_OF_CAPTAIN_PROTON, CategoryTitle.THE_ADVENTURES_OF_FLOTTER, CategoryTitle.PARIS_042);

	private final TemplateFinder templateFinder;

	private final CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	public CharacterTemplateFlagsEnrichingProcessor(TemplateFinder templateFinder,
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

		if (pageTitle.contains(MIRROR) || templateFinder.findTemplate(page, TemplateTitle.MIRROR).isPresent()
				|| categoryNameList.contains(CategoryTitle.MIRROR_UNIVERSE_INHABITANTS)) {
			characterTemplate.setMirror(true);
		}

		if (pageTitle.contains(ALTERNATE_REALITY) || templateFinder.findTemplate(page, TemplateTitle.ALT_REALITY).isPresent()
				|| categoryNameList.stream().anyMatch(categoryName -> categoryName.contains(ALTERNATE_REALITY))) {
			characterTemplate.setAlternateReality(true);
		}

		if (categoryNameList.stream().anyMatch(HOLOGRAMS_CATEGORIES::contains)) {
			characterTemplate.setHologram(true);
		}

		if (categoryNameList.stream().anyMatch(FICTIONAL_CHARACTERS_CATEGORIES::contains)) {
			characterTemplate.setFictionalCharacter(true);
		}
	}

}
