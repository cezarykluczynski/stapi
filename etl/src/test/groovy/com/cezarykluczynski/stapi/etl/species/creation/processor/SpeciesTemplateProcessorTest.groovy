package com.cezarykluczynski.stapi.etl.species.creation.processor

import com.cezarykluczynski.stapi.etl.template.species.dto.SpeciesTemplate
import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject
import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.species.entity.Species
import com.cezarykluczynski.stapi.util.AbstractSpeciesTest

class SpeciesTemplateProcessorTest extends AbstractSpeciesTest {

	private UidGenerator uidGeneratorMock

	private SpeciesTemplateProcessor speciesTemplateProcessor

	private final Page page = Mock()

	void setup() {
		uidGeneratorMock = Mock()
		speciesTemplateProcessor = new SpeciesTemplateProcessor(uidGeneratorMock)
	}

	void "converts SpeciesTemplate to Species"() {
		given:
		AstronomicalObject homeworld = Mock()
		AstronomicalObject quadrant = Mock()

		SpeciesTemplate speciesTemplate = new SpeciesTemplate(
				name: NAME,
				page: page,
				homeworld: homeworld,
				quadrant: quadrant,
				extinctSpecies: EXTINCT_SPECIES,
				warpCapableSpecies: WARP_CAPABLE_SPECIES,
				extraGalacticSpecies: EXTRA_GALACTIC_SPECIES,
				humanoidSpecies: HUMANOID_SPECIES,
				reptilianSpecies: REPTILIAN_SPECIES,
				nonCorporealSpecies: NON_CORPOREAL_SPECIES,
				shapeshiftingSpecies: SHAPESHIFTING_SPECIES,
				spaceborneSpecies: SPACEBORNE_SPECIES,
				telepathicSpecies: TELEPATHIC_SPECIES,
				transDimensionalSpecies: TRANS_DIMENSIONAL_SPECIES,
				unnamedSpecies: UNNAMED_SPECIES,
				alternateReality: ALTERNATE_REALITY)

		when:
		Species species = speciesTemplateProcessor.process(speciesTemplate)

		then:
		1 * uidGeneratorMock.generateFromPage(page, Species) >> UID
		0 * _
		species.name == NAME
		species.uid == UID
		species.page == page
		species.homeworld == homeworld
		species.quadrant == quadrant
		species.extinctSpecies == EXTINCT_SPECIES
		species.warpCapableSpecies == WARP_CAPABLE_SPECIES
		species.extraGalacticSpecies == EXTRA_GALACTIC_SPECIES
		species.humanoidSpecies == HUMANOID_SPECIES
		species.reptilianSpecies == REPTILIAN_SPECIES
		species.nonCorporealSpecies == NON_CORPOREAL_SPECIES
		species.shapeshiftingSpecies == SHAPESHIFTING_SPECIES
		species.spaceborneSpecies == SPACEBORNE_SPECIES
		species.telepathicSpecies == TELEPATHIC_SPECIES
		species.transDimensionalSpecies == TRANS_DIMENSIONAL_SPECIES
		species.unnamedSpecies == UNNAMED_SPECIES
		species.alternateReality == ALTERNATE_REALITY
	}

}
