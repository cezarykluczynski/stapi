package com.cezarykluczynski.stapi.etl.template.planet.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.planet.dto.PlanetTemplate;
import com.cezarykluczynski.stapi.etl.template.planet.dto.enums.AstronomicalObjectType;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class PlanetTemplateWikitextEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Page, PlanetTemplate>> {

	private final AstronomicalObjectWikitextProcessor astronomicalObjectWikitextProcessor;

	public PlanetTemplateWikitextEnrichingProcessor(AstronomicalObjectWikitextProcessor astronomicalObjectWikitextProcessor) {
		this.astronomicalObjectWikitextProcessor = astronomicalObjectWikitextProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Page, PlanetTemplate> enrichablePair) throws Exception {
		Page page = enrichablePair.getInput();
		PlanetTemplate planetTemplate = enrichablePair.getOutput();
		if (planetTemplate.getAstronomicalObjectType() == null || AstronomicalObjectType.PLANET.equals(planetTemplate.getAstronomicalObjectType())) {
			String wikitext = StringUtils.substring(StringUtils.substringAfter(page.getWikitext(), "'''"), 0, 200);
			AstronomicalObjectType astronomicalObjectTypeFromProcessor = astronomicalObjectWikitextProcessor.process(wikitext);

			if (astronomicalObjectTypeFromProcessor != null) {
				planetTemplate.setAstronomicalObjectType(astronomicalObjectTypeFromProcessor);
			}
		}
	}
}
