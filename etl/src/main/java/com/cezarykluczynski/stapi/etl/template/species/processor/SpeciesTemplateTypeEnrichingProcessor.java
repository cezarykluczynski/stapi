package com.cezarykluczynski.stapi.etl.template.species.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.common.service.EntityLookupByNameService;
import com.cezarykluczynski.stapi.etl.template.species.dto.SpeciesTemplate;
import com.cezarykluczynski.stapi.etl.template.species.service.SpeciesTypeDetector;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.util.constant.PageTitle;
import org.springframework.stereotype.Service;

@Service
public class SpeciesTemplateTypeEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Page, SpeciesTemplate>> {

	private static final MediaWikiSource SOURCE = MediaWikiSource.MEMORY_ALPHA_EN;

	private final SpeciesTypeDetector speciesTypeDetector;

	private final EntityLookupByNameService entityLookupByNameService;

	public SpeciesTemplateTypeEnrichingProcessor(SpeciesTypeDetector speciesTypeDetector, EntityLookupByNameService entityLookupByNameService) {
		this.speciesTypeDetector = speciesTypeDetector;
		this.entityLookupByNameService = entityLookupByNameService;
	}

	@Override
	public void enrich(EnrichablePair<Page, SpeciesTemplate> enrichablePair) throws Exception {
		SpeciesTemplate speciesTemplate = enrichablePair.getOutput();
		Page page = enrichablePair.getInput();

		if (speciesTypeDetector.isDeltaQuadrantSpecies(page)) {
			speciesTemplate.setQuadrant(entityLookupByNameService.findAstronomicalObjectByName(PageTitle.DELTA_QUADRANT, SOURCE).orElse(null));
		}

		if (speciesTypeDetector.isGammaQuadrantSpecies(page)) {
			speciesTemplate.setQuadrant(entityLookupByNameService.findAstronomicalObjectByName(PageTitle.GAMMA_QUADRANT, SOURCE).orElse(null));
		}

		speciesTemplate.setWarpCapableSpecies(speciesTypeDetector.isWarpCapableSpecies(page));
		speciesTemplate.setExtraGalacticSpecies(speciesTypeDetector.isExtraGalacticSpecies(page));
		speciesTemplate.setHumanoidSpecies(speciesTypeDetector.isHumanoidSpecies(page));
		speciesTemplate.setReptilianSpecies(speciesTypeDetector.isReptilianSpecies(page));
		speciesTemplate.setNonCorporealSpecies(speciesTypeDetector.isNonCorporealSpecies(page));
		speciesTemplate.setShapeshiftingSpecies(speciesTypeDetector.isShapeshiftingSpecies(page));
		speciesTemplate.setSpaceborneSpecies(speciesTypeDetector.isSpaceborneSpecies(page));
		speciesTemplate.setTelepathicSpecies(speciesTypeDetector.isTelepathicSpecies(page));
		speciesTemplate.setTransDimensionalSpecies(speciesTypeDetector.isTransDimensionalSpecies(page));
		speciesTemplate.setUnnamedSpecies(speciesTypeDetector.isUnnamedSpecies(page));
	}

}
