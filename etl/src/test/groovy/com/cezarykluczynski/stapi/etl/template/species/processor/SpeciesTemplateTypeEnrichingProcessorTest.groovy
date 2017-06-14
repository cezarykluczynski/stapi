package com.cezarykluczynski.stapi.etl.template.species.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.service.EntityLookupByNameService
import com.cezarykluczynski.stapi.etl.template.species.dto.SpeciesTemplate
import com.cezarykluczynski.stapi.etl.template.species.service.SpeciesTypeDetector
import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.util.AbstractSpeciesTest
import com.cezarykluczynski.stapi.util.constant.PageTitle

class SpeciesTemplateTypeEnrichingProcessorTest extends AbstractSpeciesTest {

	private SpeciesTypeDetector speciesTypeDetectorMock

	private EntityLookupByNameService entityLookupByNameServiceMock

	private SpeciesTemplateTypeEnrichingProcessor speciesTemplateTypeEnrichingProcessor

	void setup() {
		speciesTypeDetectorMock = Mock()
		entityLookupByNameServiceMock = Mock()
		speciesTemplateTypeEnrichingProcessor = new SpeciesTemplateTypeEnrichingProcessor(speciesTypeDetectorMock, entityLookupByNameServiceMock)
	}

	void "tries to set quadrant to Delta Quadrant when species is from Delta Quadrant"() {
		given:
		Page page = new Page()
		SpeciesTemplate speciesTemplate = new SpeciesTemplate()
		AstronomicalObject quadrant = Mock()

		when:
		speciesTemplateTypeEnrichingProcessor.enrich(EnrichablePair.of(page, speciesTemplate))

		then:
		1 * speciesTypeDetectorMock.isDeltaQuadrantSpecies(page) >> true
		1 * entityLookupByNameServiceMock.findAstronomicalObjectByName(PageTitle.DELTA_QUADRANT, SpeciesTemplateTypeEnrichingProcessor.SOURCE) >>
				Optional.of(quadrant)
		speciesTemplate.quadrant == quadrant
	}

	void "tolerates empty Delta Quadrant"() {
		given:
		Page page = new Page()
		SpeciesTemplate speciesTemplate = new SpeciesTemplate()

		when:
		speciesTemplateTypeEnrichingProcessor.enrich(EnrichablePair.of(page, speciesTemplate))

		then:
		1 * speciesTypeDetectorMock.isDeltaQuadrantSpecies(page) >> true
		1 * entityLookupByNameServiceMock.findAstronomicalObjectByName(PageTitle.DELTA_QUADRANT, SpeciesTemplateTypeEnrichingProcessor.SOURCE) >>
				Optional.empty()
		speciesTemplate.quadrant == null
		notThrown(Exception)
	}

	void "tries to set quadrant to Gamma Quadrant when species is from Gamma Quadrant"() {
		given:
		Page page = new Page()
		SpeciesTemplate speciesTemplate = new SpeciesTemplate()
		AstronomicalObject quadrant = Mock()

		when:
		speciesTemplateTypeEnrichingProcessor.enrich(EnrichablePair.of(page, speciesTemplate))

		then:
		1 * speciesTypeDetectorMock.isGammaQuadrantSpecies(page) >> true
		1 * entityLookupByNameServiceMock.findAstronomicalObjectByName(PageTitle.GAMMA_QUADRANT, SpeciesTemplateTypeEnrichingProcessor.SOURCE) >>
				Optional.of(quadrant)
		speciesTemplate.quadrant == quadrant
	}

	void "tolerates empty Gamma Quadrant"() {
		given:
		Page page = new Page()
		SpeciesTemplate speciesTemplate = new SpeciesTemplate()

		when:
		speciesTemplateTypeEnrichingProcessor.enrich(EnrichablePair.of(page, speciesTemplate))

		then:
		1 * speciesTypeDetectorMock.isGammaQuadrantSpecies(page) >> true
		1 * entityLookupByNameServiceMock.findAstronomicalObjectByName(PageTitle.GAMMA_QUADRANT, SpeciesTemplateTypeEnrichingProcessor.SOURCE) >>
				Optional.empty()
		speciesTemplate.quadrant == null
		notThrown(Exception)
	}

	void "sets flags from SpeciesTypeDetector"() {
		given:
		Page page = new Page()
		SpeciesTemplate speciesTemplate = new SpeciesTemplate()

		when:
		speciesTemplateTypeEnrichingProcessor.enrich(EnrichablePair.of(page, speciesTemplate))

		then:
		1 * speciesTypeDetectorMock.isDeltaQuadrantSpecies(page) >> false
		1 * speciesTypeDetectorMock.isGammaQuadrantSpecies(page) >> false
		1 * speciesTypeDetectorMock.isWarpCapableSpecies(page) >> WARP_CAPABLE_SPECIES
		1 * speciesTypeDetectorMock.isExtraGalacticSpecies(page) >> EXTRA_GALACTIC_SPECIES
		1 * speciesTypeDetectorMock.isHumanoidSpecies(page) >> HUMANOID_SPECIES
		1 * speciesTypeDetectorMock.isReptilianSpecies(page) >> REPTILIAN_SPECIES
		1 * speciesTypeDetectorMock.isNonCorporealSpecies(page) >> NON_CORPOREAL_SPECIES
		1 * speciesTypeDetectorMock.isShapeshiftingSpecies(page) >> SHAPESHIFTING_SPECIES
		1 * speciesTypeDetectorMock.isSpaceborneSpecies(page) >> SPACEBORNE_SPECIES
		1 * speciesTypeDetectorMock.isTelepathicSpecies(page) >> TELEPATHIC_SPECIES
		1 * speciesTypeDetectorMock.isTransDimensionalSpecies(page) >> TRANS_DIMENSIONAL_SPECIES
		1 * speciesTypeDetectorMock.isUnnamedSpecies(page) >> UNNAMED_SPECIES
		0 * _
		speciesTemplate.warpCapableSpecies == WARP_CAPABLE_SPECIES
		speciesTemplate.extraGalacticSpecies == EXTRA_GALACTIC_SPECIES
		speciesTemplate.humanoidSpecies == HUMANOID_SPECIES
		speciesTemplate.reptilianSpecies == REPTILIAN_SPECIES
		speciesTemplate.nonCorporealSpecies == NON_CORPOREAL_SPECIES
		speciesTemplate.shapeshiftingSpecies == SHAPESHIFTING_SPECIES
		speciesTemplate.spaceborneSpecies == SPACEBORNE_SPECIES
		speciesTemplate.telepathicSpecies == TELEPATHIC_SPECIES
		speciesTemplate.transDimensionalSpecies == TRANS_DIMENSIONAL_SPECIES
		speciesTemplate.unnamedSpecies == UNNAMED_SPECIES
	}

}
