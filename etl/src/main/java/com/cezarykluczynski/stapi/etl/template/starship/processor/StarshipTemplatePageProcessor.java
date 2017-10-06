package com.cezarykluczynski.stapi.etl.template.starship.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.spacecraft.creation.processor.SpacecraftCategoriesEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.spacecraft.creation.service.SpacecraftPageFilter;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.etl.template.spacecraft.processor.SpacecraftTemplateCompositeEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.starship.dto.StarshipTemplate;
import com.cezarykluczynski.stapi.etl.util.TitleUtil;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StarshipTemplatePageProcessor implements ItemProcessor<Page, StarshipTemplate> {

	private final SpacecraftPageFilter spacecraftPageFilter;

	private final TemplateFinder templateFinder;

	private final PageBindingService pageBindingService;

	private final SpacecraftTemplateCompositeEnrichingProcessor spacecraftTemplateCompositeEnrichingProcessor;

	private final CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	private final SpacecraftCategoriesEnrichingProcessor spacecraftCategoriesEnrichingProcessor;

	public StarshipTemplatePageProcessor(SpacecraftPageFilter spacecraftPageFilter, TemplateFinder templateFinder,
			PageBindingService pageBindingService, SpacecraftTemplateCompositeEnrichingProcessor spacecraftTemplateCompositeEnrichingProcessor,
			CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor,
			SpacecraftCategoriesEnrichingProcessor spacecraftCategoriesEnrichingProcessor) {
		this.spacecraftPageFilter = spacecraftPageFilter;
		this.templateFinder = templateFinder;
		this.pageBindingService = pageBindingService;
		this.spacecraftTemplateCompositeEnrichingProcessor = spacecraftTemplateCompositeEnrichingProcessor;
		this.categoryTitlesExtractingProcessor = categoryTitlesExtractingProcessor;
		this.spacecraftCategoriesEnrichingProcessor = spacecraftCategoriesEnrichingProcessor;
	}

	@Override
	public StarshipTemplate process(Page item) throws Exception {
		if (spacecraftPageFilter.shouldBeFilteredOut(item)) {
			return null;
		}

		StarshipTemplate starshipTemplate = new StarshipTemplate();
		starshipTemplate.setName(TitleUtil.getNameFromTitle(item.getTitle()));
		starshipTemplate.setPage(pageBindingService.fromPageToPageEntity(item));

		Optional<Template> starshipTemplateOptional = templateFinder.findTemplate(item, TemplateTitle.SIDEBAR_STARSHIP);
		Optional<Template> stationTemplateOptional = templateFinder.findTemplate(item, TemplateTitle.SIDEBAR_STATION);

		if (starshipTemplateOptional.isPresent()) {
			spacecraftTemplateCompositeEnrichingProcessor.enrich(EnrichablePair.of(starshipTemplateOptional.get(), starshipTemplate));
		}

		if (stationTemplateOptional.isPresent()) {
			spacecraftTemplateCompositeEnrichingProcessor.enrich(EnrichablePair.of(stationTemplateOptional.get(), starshipTemplate));
		}

		List<String> categoryTitleList = categoryTitlesExtractingProcessor.process(item.getCategories());
		spacecraftCategoriesEnrichingProcessor.enrich(EnrichablePair.of(categoryTitleList, starshipTemplate));

		return starshipTemplate;
	}

}
