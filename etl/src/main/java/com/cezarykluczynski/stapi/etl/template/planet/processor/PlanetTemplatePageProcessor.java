package com.cezarykluczynski.stapi.etl.template.planet.processor;

import com.cezarykluczynski.stapi.etl.astronomical_object.creation.service.AstronomicalObjectPageFilter;
import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.template.planet.dto.PlanetTemplate;
import com.cezarykluczynski.stapi.etl.template.planet.dto.PlanetTemplateParameter;
import com.cezarykluczynski.stapi.etl.template.planet.dto.enums.AstronomicalObjectType;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.etl.util.TitleUtil;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class PlanetTemplatePageProcessor implements ItemProcessor<Page, PlanetTemplate> {

	private final AstronomicalObjectPageFilter astronomicalObjectPageFilter;

	private final TemplateFinder templateFinder;

	private final PageBindingService pageBindingService;

	private final AstronomicalObjectTypeProcessor astronomicalObjectTypeProcessor;

	private final AstronomicalObjectTypeEnrichingProcessor astronomicalObjectTypeEnrichingProcessor;

	private final AstronomicalObjectBestPickEnrichingProcessor astronomicalObjectBestPickEnrichingProcessor;

	private final PlanetTemplateWikitextEnrichingProcessor planetTemplateWikitextEnrichingProcessor;

	public PlanetTemplatePageProcessor(AstronomicalObjectPageFilter astronomicalObjectPageFilter, TemplateFinder templateFinder,
			PageBindingService pageBindingService, AstronomicalObjectTypeProcessor astronomicalObjectTypeProcessor,
			AstronomicalObjectTypeEnrichingProcessor astronomicalObjectTypeEnrichingProcessor,
			AstronomicalObjectBestPickEnrichingProcessor astronomicalObjectBestPickEnrichingProcessor,
			PlanetTemplateWikitextEnrichingProcessor planetTemplateWikitextEnrichingProcessor) {
		this.astronomicalObjectPageFilter = astronomicalObjectPageFilter;
		this.templateFinder = templateFinder;
		this.pageBindingService = pageBindingService;
		this.astronomicalObjectTypeProcessor = astronomicalObjectTypeProcessor;
		this.astronomicalObjectTypeEnrichingProcessor = astronomicalObjectTypeEnrichingProcessor;
		this.astronomicalObjectBestPickEnrichingProcessor = astronomicalObjectBestPickEnrichingProcessor;
		this.planetTemplateWikitextEnrichingProcessor = planetTemplateWikitextEnrichingProcessor;
	}

	@Override
	public PlanetTemplate process(Page item) throws Exception {
		if (astronomicalObjectPageFilter.shouldBeFilteredOut(item)) {
			return null;
		}

		PlanetTemplate planetTemplate = new PlanetTemplate();
		planetTemplate.setName(TitleUtil.getNameFromTitle(item.getTitle()));
		planetTemplate.setPage(pageBindingService.fromPageToPageEntity(item));

		astronomicalObjectTypeEnrichingProcessor.enrich(EnrichablePair.of(item, planetTemplate));

		Optional<Template> templateOptional = templateFinder.findTemplate(item, TemplateTitle.SIDEBAR_PLANET);
		if (!templateOptional.isPresent()) {
			planetTemplateWikitextEnrichingProcessor.enrich(EnrichablePair.of(item, planetTemplate));
			return planetTemplate;
		}
		Template template = templateOptional.get();

		for (Template.Part part : template.getParts()) {
			String key = part.getKey();
			String value = part.getValue();

			if (PlanetTemplateParameter.CLASS.equals(key) || PlanetTemplateParameter.TYPE.equals(key)) {
				AstronomicalObjectType astronomicalObjectTypeFromProcessor = astronomicalObjectTypeProcessor.process(value);
				AstronomicalObjectType currentAstronomicalObjectType = planetTemplate.getAstronomicalObjectType();
				astronomicalObjectBestPickEnrichingProcessor.enrich(EnrichablePair.of(Pair
						.of(currentAstronomicalObjectType, astronomicalObjectTypeFromProcessor), planetTemplate));
			}
		}

		planetTemplateWikitextEnrichingProcessor.enrich(EnrichablePair.of(item, planetTemplate));

		if (planetTemplate.getAstronomicalObjectType() == null) {
			log.info("Could not get astronomical object type for {}", item.getTitle());
		}

		return planetTemplate;
	}

}
