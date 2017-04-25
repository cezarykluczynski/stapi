package com.cezarykluczynski.stapi.server.species.mapper

import com.cezarykluczynski.stapi.client.v1.soap.SpeciesFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesFull
import com.cezarykluczynski.stapi.model.species.dto.SpeciesRequestDTO
import com.cezarykluczynski.stapi.model.species.entity.Species
import org.mapstruct.factory.Mappers

class SpeciesFullSoapMapperTest extends AbstractSpeciesMapperTest {

	private SpeciesFullSoapMapper speciesFullSoapMapper

	void setup() {
		speciesFullSoapMapper = Mappers.getMapper(SpeciesFullSoapMapper)
	}

	void "maps SOAP SpeciesFullRequest to SpeciesBaseRequestDTO"() {
		given:
		SpeciesFullRequest speciesFullRequest = new SpeciesFullRequest(uid: UID)

		when:
		SpeciesRequestDTO speciesRequestDTO = speciesFullSoapMapper.mapFull speciesFullRequest

		then:
		speciesRequestDTO.uid == UID
	}

	void "maps DB entity to full SOAP entity"() {
		given:
		Species species = createSpecies()

		when:
		SpeciesFull speciesFull = speciesFullSoapMapper.mapFull(species)

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
