package com.cezarykluczynski.stapi.server.species.mapper

import com.cezarykluczynski.stapi.client.v1.soap.Species as SOAPSpecies
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesRequest
import com.cezarykluczynski.stapi.model.species.dto.SpeciesRequestDTO
import com.cezarykluczynski.stapi.model.species.entity.Species as DBSpecies
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class SpeciesSoapMapperTest extends AbstractSpeciesMapperTest {

	private SpeciesSoapMapper speciesSoapMapper

	void setup() {
		speciesSoapMapper = Mappers.getMapper(SpeciesSoapMapper)
	}

	void "maps SOAP SpeciesRequest to SpeciesRequestDTO"() {
		given:
		SpeciesRequest speciesRequest = new SpeciesRequest(
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
		SpeciesRequestDTO speciesRequestDTO = speciesSoapMapper.map speciesRequest

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

	void "maps DB entity to SOAP entity"() {
		given:
		DBSpecies dbSpecies = createSpecies()

		when:
		SOAPSpecies soapSpecies = speciesSoapMapper.map(Lists.newArrayList(dbSpecies))[0]

		then:
		soapSpecies.name == NAME
		soapSpecies.guid == GUID
		soapSpecies.homeworld != null
		soapSpecies.quadrant != null
		soapSpecies.extinctSpecies == EXTINCT_SPECIES
		soapSpecies.warpCapableSpecies == WARP_CAPABLE_SPECIES
		soapSpecies.extraGalacticSpecies == EXTRA_GALACTIC_SPECIES
		soapSpecies.humanoidSpecies == HUMANOID_SPECIES
		soapSpecies.reptilianSpecies == REPTILIAN_SPECIES
		soapSpecies.nonCorporealSpecies == NON_CORPOREAL_SPECIES
		soapSpecies.shapeshiftingSpecies == SHAPESHIFTING_SPECIES
		soapSpecies.spaceborneSpecies == SPACEBORNE_SPECIES
		soapSpecies.telepathicSpecies == TELEPATHIC_SPECIES
		soapSpecies.transDimensionalSpecies == TRANS_DIMENSIONAL_SPECIES
		soapSpecies.unnamedSpecies == UNNAMED_SPECIES
		soapSpecies.alternateReality == ALTERNATE_REALITY
		soapSpecies.characterHeaders.size() == dbSpecies.characters.size()
	}

}
