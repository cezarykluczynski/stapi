package com.cezarykluczynski.stapi.server.species.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.SpeciesFull
import com.cezarykluczynski.stapi.model.species.entity.Species
import org.mapstruct.factory.Mappers

class SpeciesFullRestMapperTest extends AbstractSpeciesMapperTest {

	private SpeciesFullRestMapper speciesFullRestMapper

	void setup() {
		speciesFullRestMapper = Mappers.getMapper(SpeciesFullRestMapper)
	}

	void "maps DB entity to full REST entity"() {
		given:
		Species species = createSpecies()

		when:
		SpeciesFull speciesFull = speciesFullRestMapper.mapFull(species)

		then:
		speciesFull.name == NAME
		speciesFull.uid == UID
		speciesFull.homeworld != null
		speciesFull.quadrant != null
		speciesFull.extinctSpecies == EXTINCT_SPECIES
		speciesFull.warpCapableSpecies == WARP_CAPABLE_SPECIES
		speciesFull.extraGalacticSpecies == EXTRA_GALACTIC_SPECIES
		speciesFull.humanoidSpecies == HUMANOID_SPECIES
		speciesFull.reptilianSpecies == REPTILIAN_SPECIES
		speciesFull.nonCorporealSpecies == NON_CORPOREAL_SPECIES
		speciesFull.shapeshiftingSpecies == SHAPESHIFTING_SPECIES
		speciesFull.spaceborneSpecies == SPACEBORNE_SPECIES
		speciesFull.telepathicSpecies == TELEPATHIC_SPECIES
		speciesFull.transDimensionalSpecies == TRANS_DIMENSIONAL_SPECIES
		speciesFull.unnamedSpecies == UNNAMED_SPECIES
		speciesFull.alternateReality == ALTERNATE_REALITY
		speciesFull.characters.size() == species.characters.size()
	}

}
