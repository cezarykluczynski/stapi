package com.cezarykluczynski.stapi.etl.template.planet.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.planet.dto.PlanetTemplate;
import com.cezarykluczynski.stapi.etl.template.planet.dto.enums.AstronomicalObjectType;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AstronomicalObjectTypeEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Page, PlanetTemplate>> {

	private final CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	public AstronomicalObjectTypeEnrichingProcessor(CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor) {
		this.categoryTitlesExtractingProcessor = categoryTitlesExtractingProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Page, PlanetTemplate> enrichablePair) throws Exception {
		Page page = enrichablePair.getInput();
		PlanetTemplate planetTemplate = enrichablePair.getOutput();

		List<String> categoryTitleList = categoryTitlesExtractingProcessor.process(page.getCategories());

		if (categoryTitleList.contains(CategoryTitle.PLANETS) || categoryTitleList.contains(CategoryTitle.PLANETS_RETCONNED)
				|| categoryTitleList.contains(CategoryTitle.UNNAMED_PLANETS)) {
			planetTemplate.setAstronomicalObjectType(AstronomicalObjectType.PLANET);
		} else if (categoryTitleList.contains(CategoryTitle.ASTEROIDS)) {
			planetTemplate.setAstronomicalObjectType(AstronomicalObjectType.ASTEROID);
		} else if (categoryTitleList.contains(CategoryTitle.ASTEROID_BELTS)) {
			planetTemplate.setAstronomicalObjectType(AstronomicalObjectType.ASTEROID_BELT);
		} else if (categoryTitleList.contains(CategoryTitle.COMETS)) {
			planetTemplate.setAstronomicalObjectType(AstronomicalObjectType.COMET);
		} else if (categoryTitleList.contains(CategoryTitle.CLUSTERS)) {
			planetTemplate.setAstronomicalObjectType(AstronomicalObjectType.CLUSTER);
		} else if (categoryTitleList.contains(CategoryTitle.CONSTELLATIONS)) {
			planetTemplate.setAstronomicalObjectType(AstronomicalObjectType.CONSTELLATION);
		} else if (categoryTitleList.contains(CategoryTitle.GALAXIES)) {
			planetTemplate.setAstronomicalObjectType(AstronomicalObjectType.GALAXY);
		} else if (categoryTitleList.contains(CategoryTitle.MOONS)) {
			planetTemplate.setAstronomicalObjectType(AstronomicalObjectType.MOON);
		} else if (categoryTitleList.contains(CategoryTitle.NEBULAE) || categoryTitleList.contains(CategoryTitle.NEBULAE_RETCONNED)) {
			planetTemplate.setAstronomicalObjectType(AstronomicalObjectType.NEBULA);
		} else if (categoryTitleList.contains(CategoryTitle.PLANETOIDS)) {
			planetTemplate.setAstronomicalObjectType(AstronomicalObjectType.PLANETOID);
		} else if (categoryTitleList.contains(CategoryTitle.QUASARS)) {
			planetTemplate.setAstronomicalObjectType(AstronomicalObjectType.QUASAR);
		} else if (categoryTitleList.contains(CategoryTitle.REGIONS)) {
			planetTemplate.setAstronomicalObjectType(AstronomicalObjectType.REGION);
		} else if (categoryTitleList.contains(CategoryTitle.SECTORS)) {
			planetTemplate.setAstronomicalObjectType(AstronomicalObjectType.SECTOR);
		} else if (categoryTitleList.contains(CategoryTitle.STAR_SYSTEMS)) {
			planetTemplate.setAstronomicalObjectType(AstronomicalObjectType.STAR_SYSTEM);
		} else if (categoryTitleList.contains(CategoryTitle.STARS)) {
			planetTemplate.setAstronomicalObjectType(AstronomicalObjectType.STAR);
		}
	}

}
