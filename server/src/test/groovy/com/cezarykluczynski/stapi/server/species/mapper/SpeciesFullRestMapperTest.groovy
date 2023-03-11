package com.cezarykluczynski.stapi.server.species.mapper

import com.cezarykluczynski.stapi.client.rest.model.SpeciesFull
import com.cezarykluczynski.stapi.client.rest.model.SpeciesV2Full
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

	void "maps DB entity to full REST V2 entity"() {
		given:
		Species species = createSpecies()

		when:
		SpeciesV2Full speciesV2Full = speciesFullRestMapper.mapV2Full(species)

		then:
		speciesV2Full.name == NAME
		speciesV2Full.uid == UID
		speciesV2Full.homeworld != null
		speciesV2Full.quadrant != null
		speciesV2Full.extinctSpecies == EXTINCT_SPECIES
		speciesV2Full.warpCapableSpecies == WARP_CAPABLE_SPECIES
		speciesV2Full.extraGalacticSpecies == EXTRA_GALACTIC_SPECIES
		speciesV2Full.humanoidSpecies == HUMANOID_SPECIES
		speciesV2Full.reptilianSpecies == REPTILIAN_SPECIES
		speciesV2Full.avianSpecies == AVIAN_SPECIES
		speciesV2Full.nonCorporealSpecies == NON_CORPOREAL_SPECIES
		speciesV2Full.shapeshiftingSpecies == SHAPESHIFTING_SPECIES
		speciesV2Full.spaceborneSpecies == SPACEBORNE_SPECIES
		speciesV2Full.telepathicSpecies == TELEPATHIC_SPECIES
		speciesV2Full.transDimensionalSpecies == TRANS_DIMENSIONAL_SPECIES
		speciesV2Full.unnamedSpecies == UNNAMED_SPECIES
		speciesV2Full.alternateReality == ALTERNATE_REALITY
		speciesV2Full.characters.size() == species.characters.size()
	}

}
