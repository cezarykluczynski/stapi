package com.cezarykluczynski.stapi.server.species.mapper

import com.cezarykluczynski.stapi.client.v1.soap.SpeciesBase as SpeciesBase
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesBaseRequest
import com.cezarykluczynski.stapi.model.species.dto.SpeciesRequestDTO
import com.cezarykluczynski.stapi.model.species.entity.Species as Species
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class SpeciesBaseSoapMapperTest extends AbstractSpeciesMapperTest {

	private SpeciesBaseSoapMapper speciesBaseSoapMapper

	void setup() {
		speciesBaseSoapMapper = Mappers.getMapper(SpeciesBaseSoapMapper)
	}

	void "maps SOAP SpeciesRequest to SpeciesRequestDTO"() {
		given:
		SpeciesBaseRequest speciesBaseRequest = new SpeciesBaseRequest(
				name: NAME,
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
		SpeciesRequestDTO speciesRequestDTO = speciesBaseSoapMapper.mapBase speciesBaseRequest

		then:
		speciesRequestDTO.name == NAME
		speciesRequestDTO.extinctSpecies == EXTINCT_SPECIES
		speciesRequestDTO.warpCapableSpecies == WARP_CAPABLE_SPECIES
		speciesRequestDTO.extraGalacticSpecies == EXTRA_GALACTIC_SPECIES
		speciesRequestDTO.humanoidSpecies == HUMANOID_SPECIES
		speciesRequestDTO.reptilianSpecies == REPTILIAN_SPECIES
		speciesRequestDTO.nonCorporealSpecies == NON_CORPOREAL_SPECIES
		speciesRequestDTO.shapeshiftingSpecies == SHAPESHIFTING_SPECIES
		speciesRequestDTO.spaceborneSpecies == SPACEBORNE_SPECIES
		speciesRequestDTO.telepathicSpecies == TELEPATHIC_SPECIES
		speciesRequestDTO.transDimensionalSpecies == TRANS_DIMENSIONAL_SPECIES
		speciesRequestDTO.unnamedSpecies == UNNAMED_SPECIES
		speciesRequestDTO.alternateReality == ALTERNATE_REALITY
	}

	void "maps DB entity to base SOAP entity"() {
		given:
		Species species = createSpecies()

		when:
		SpeciesBase speciesBase = speciesBaseSoapMapper.mapBase(Lists.newArrayList(species))[0]

		then:
		speciesBase.name == NAME
		speciesBase.uid == UID
		speciesBase.homeworld != null
		speciesBase.quadrant != null
		speciesBase.extinctSpecies == EXTINCT_SPECIES
		speciesBase.warpCapableSpecies == WARP_CAPABLE_SPECIES
		speciesBase.extraGalacticSpecies == EXTRA_GALACTIC_SPECIES
		speciesBase.humanoidSpecies == HUMANOID_SPECIES
		speciesBase.reptilianSpecies == REPTILIAN_SPECIES
		speciesBase.nonCorporealSpecies == NON_CORPOREAL_SPECIES
		speciesBase.shapeshiftingSpecies == SHAPESHIFTING_SPECIES
		speciesBase.spaceborneSpecies == SPACEBORNE_SPECIES
		speciesBase.telepathicSpecies == TELEPATHIC_SPECIES
		speciesBase.transDimensionalSpecies == TRANS_DIMENSIONAL_SPECIES
		speciesBase.unnamedSpecies == UNNAMED_SPECIES
		speciesBase.alternateReality == ALTERNATE_REALITY
	}

}
