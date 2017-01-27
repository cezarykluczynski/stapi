package com.cezarykluczynski.stapi.etl.template.planet.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.planet.dto.PlanetTemplate;
import com.cezarykluczynski.stapi.etl.template.planet.dto.enums.AstronomicalObjectType;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryName;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class AstronomicalObjectTypeEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Page, PlanetTemplate>> {

	private CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	@Inject
	public AstronomicalObjectTypeEnrichingProcessor(CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor) {
		this.categoryTitlesExtractingProcessor = categoryTitlesExtractingProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Page, PlanetTemplate> enrichablePair) throws Exception {
		Page page = enrichablePair.getInput();
		PlanetTemplate planetTemplate = enrichablePair.getOutput();

		List<String> categoryTitleList = categoryTitlesExtractingProcessor.process(page.getCategories());

		if (categoryTitleList.contains(CategoryName.PLANETS)) {
			planetTemplate.setAstronomicalObjectType(AstronomicalObjectType.PLANET);
		} else if (categoryTitleList.contains(CategoryName.ASTEROIDS)) {
			planetTemplate.setAstronomicalObjectType(AstronomicalObjectType.ASTEROID);
		} else if (categoryTitleList.contains(CategoryName.ASTEROID_BELTS)) {
			planetTemplate.setAstronomicalObjectType(AstronomicalObjectType.ASTEROID_BELT);
		} else if (categoryTitleList.contains(CategoryName.COMETS)) {
			planetTemplate.setAstronomicalObjectType(AstronomicalObjectType.COMET);
		} else if (categoryTitleList.contains(CategoryName.CONSTELLATIONS)) {
			planetTemplate.setAstronomicalObjectType(AstronomicalObjectType.CONSTELLATION);
		} else if (categoryTitleList.contains(CategoryName.GALAXIES)) {
			planetTemplate.setAstronomicalObjectType(AstronomicalObjectType.GALAXY);
		} else if (categoryTitleList.contains(CategoryName.MOONS)) {
			planetTemplate.setAstronomicalObjectType(AstronomicalObjectType.MOON);
		} else if (categoryTitleList.contains(CategoryName.NEBULAE)) {
			planetTemplate.setAstronomicalObjectType(AstronomicalObjectType.NEBULA);
		} else if (categoryTitleList.contains(CategoryName.PLANETOIDS)) {
			planetTemplate.setAstronomicalObjectType(AstronomicalObjectType.PLANETOID);
		} else if (categoryTitleList.contains(CategoryName.QUASARS)) {
			planetTemplate.setAstronomicalObjectType(AstronomicalObjectType.QUASAR);
		} else if (categoryTitleList.contains(CategoryName.STAR_SYSTEMS)) {
			planetTemplate.setAstronomicalObjectType(AstronomicalObjectType.STAR_SYSTEM);
		} else if (categoryTitleList.contains(CategoryName.STARS)) {
			planetTemplate.setAstronomicalObjectType(AstronomicalObjectType.STAR);
		}
	}

}
