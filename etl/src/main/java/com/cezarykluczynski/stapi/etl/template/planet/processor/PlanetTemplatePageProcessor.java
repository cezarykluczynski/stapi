package com.cezarykluczynski.stapi.etl.template.planet.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.template.planet.dto.PlanetTemplate;
import com.cezarykluczynski.stapi.etl.template.planet.dto.PlanetTemplateParameter;
import com.cezarykluczynski.stapi.etl.template.planet.dto.enums.AstronomicalObjectType;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.etl.util.TitleUtil;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PlanetTemplatePageProcessor implements ItemProcessor<Page, PlanetTemplate> {

	private static final String PLANETARY_CLASSIFICATION = "Planetary classification";
	private static final String UNNAMED_PREFIX = "Unnamed";

	private TemplateFinder templateFinder;

	private PageBindingService pageBindingService;

	private AstronomicalObjectTypeProcessor astronomicalObjectTypeProcessor;

	private AstronomicalObjectTypeEnrichingProcessor astronomicalObjectTypeEnrichingProcessor;

	private AstronomicalObjectWikitextProcessor astronomicalObjectWikitextProcessor;

	private AstronomicalObjectCompositeEnrichingProcessor astronomicalObjectCompositeEnrichingProcessor;

	@Inject
	public PlanetTemplatePageProcessor(TemplateFinder templateFinder, PageBindingService pageBindingService,
			AstronomicalObjectTypeProcessor astronomicalObjectTypeProcessor,
			AstronomicalObjectTypeEnrichingProcessor astronomicalObjectTypeEnrichingProcessor,
			AstronomicalObjectWikitextProcessor astronomicalObjectWikitextProcessor,
			AstronomicalObjectCompositeEnrichingProcessor astronomicalObjectCompositeEnrichingProcessor) {
		this.templateFinder = templateFinder;
		this.pageBindingService = pageBindingService;
		this.astronomicalObjectTypeProcessor = astronomicalObjectTypeProcessor;
		this.astronomicalObjectTypeEnrichingProcessor = astronomicalObjectTypeEnrichingProcessor;
		this.astronomicalObjectWikitextProcessor = astronomicalObjectWikitextProcessor;
		this.astronomicalObjectCompositeEnrichingProcessor = astronomicalObjectCompositeEnrichingProcessor;
	}

	@Override
	public PlanetTemplate process(Page item) throws Exception {
		if (shouldBeFilteredOut(item)) {
			return null;
		}

		PlanetTemplate planetTemplate = new PlanetTemplate();
		planetTemplate.setName(TitleUtil.getNameFromTitle(item.getTitle()));
		planetTemplate.setPage(pageBindingService.fromPageToPageEntity(item));
		planetTemplate.setProductOfRedirect(!item.getRedirectPath().isEmpty());

		astronomicalObjectTypeEnrichingProcessor.enrich(EnrichablePair.of(item, planetTemplate));

		Optional<Template> templateOptional = templateFinder.findTemplate(item, TemplateTitle.SIDEBAR_PLANET);
		if (!templateOptional.isPresent()) {
			trySetTypeFromWikitext(planetTemplate, item);
			return planetTemplate;
		}
		Template template = templateOptional.get();

		for (Template.Part part : template.getParts()) {
			String key = part.getKey();
			String value = part.getValue();

			if (PlanetTemplateParameter.CLASS.equals(key)) {
				AstronomicalObjectType astronomicalObjectTypeFromProcessor = astronomicalObjectTypeProcessor.process(value);
				AstronomicalObjectType currentAstronomicalObjectType = planetTemplate.getAstronomicalObjectType();
				astronomicalObjectCompositeEnrichingProcessor.enrich(EnrichablePair.of(Pair
						.of(currentAstronomicalObjectType, astronomicalObjectTypeFromProcessor), planetTemplate));
			}
		}

		trySetTypeFromWikitext(planetTemplate, item);

		if (planetTemplate.getAstronomicalObjectType() == null) {
			log.warn("Could not get astronomical object type for {}", item.getTitle());
		}

		return planetTemplate;
	}

	private void trySetTypeFromWikitext(PlanetTemplate planetTemplate, Page item) throws Exception {
		if (planetTemplate.getAstronomicalObjectType() == null || AstronomicalObjectType.PLANET.equals(planetTemplate.getAstronomicalObjectType())) {
			String wikitext = StringUtils.substring(StringUtils.substringAfter(item.getWikitext(), "'''"), 0, 200);
			AstronomicalObjectType astronomicalObjectTypeFromProcessor = astronomicalObjectWikitextProcessor.process(wikitext);

			if (astronomicalObjectTypeFromProcessor != null) {
				planetTemplate.setAstronomicalObjectType(astronomicalObjectTypeFromProcessor);
			}
		}
	}

	private boolean shouldBeFilteredOut(Page item) {
		String title = item.getTitle();
		if (title.startsWith(UNNAMED_PREFIX) || PLANETARY_CLASSIFICATION.equals(title)) {
			return true;
		}

		// TODO CategoryTitlesExtractingProcessor
		List<String> categoryHeaderList = item.getCategories()
				.stream()
				.map(CategoryHeader::getTitle)
				.collect(Collectors.toList());

		return categoryHeaderList.contains(CategoryTitle.PLANET_LISTS);
	}

}
