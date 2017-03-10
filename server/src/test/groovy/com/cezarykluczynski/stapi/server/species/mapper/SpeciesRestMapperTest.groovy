package com.cezarykluczynski.stapi.server.species.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.Species as RESTSpecies
import com.cezarykluczynski.stapi.model.species.dto.SpeciesRequestDTO
import com.cezarykluczynski.stapi.model.species.entity.Species as DBSpecies
import com.cezarykluczynski.stapi.server.species.dto.SpeciesRestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class SpeciesRestMapperTest extends AbstractSpeciesMapperTest {

	private SpeciesRestMapper speciesRestMapper

	void setup() {
		speciesRestMapper = Mappers.getMapper(SpeciesRestMapper)
	}

	void "maps SpeciesRestBeanParams to SpeciesRequestDTO"() {
		given:
		SpeciesRestBeanParams speciesRestBeanParams = new SpeciesRestBeanParams(
				guid: GUID,
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
		SpeciesRequestDTO speciesRequestDTO = speciesRestMapper.map speciesRestBeanParams

		then:
		speciesRequestDTO.guid == GUID
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

	void "maps DB entity to REST entity"() {
		given:
		DBSpecies dbSpecies = createSpecies()

		when:
		RESTSpecies restSpecies = speciesRestMapper.map(Lists.newArrayList(dbSpecies))[0]

		then:
		restSpecies.name == NAME
		restSpecies.guid == GUID
		restSpecies.homeworld != null
		restSpecies.quadrant != null
		restSpecies.extinctSpecies == EXTINCT_SPECIES
		restSpecies.warpCapableSpecies == WARP_CAPABLE_SPECIES
		restSpecies.extraGalacticSpecies == EXTRA_GALACTIC_SPECIES
		restSpecies.humanoidSpecies == HUMANOID_SPECIES
		restSpecies.reptilianSpecies == REPTILIAN_SPECIES
		restSpecies.nonCorporealSpecies == NON_CORPOREAL_SPECIES
		restSpecies.shapeshiftingSpecies == SHAPESHIFTING_SPECIES
		restSpecies.spaceborneSpecies == SPACEBORNE_SPECIES
		restSpecies.telepathicSpecies == TELEPATHIC_SPECIES
		restSpecies.transDimensionalSpecies == TRANS_DIMENSIONAL_SPECIES
		restSpecies.unnamedSpecies == UNNAMED_SPECIES
		restSpecies.alternateReality == ALTERNATE_REALITY
	}

}
