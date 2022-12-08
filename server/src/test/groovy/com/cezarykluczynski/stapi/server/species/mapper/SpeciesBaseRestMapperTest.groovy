package com.cezarykluczynski.stapi.server.species.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.SpeciesBase as SpeciesBase
import com.cezarykluczynski.stapi.client.v1.rest.model.SpeciesV2Base
import com.cezarykluczynski.stapi.model.species.dto.SpeciesRequestDTO
import com.cezarykluczynski.stapi.model.species.entity.Species as Species
import com.cezarykluczynski.stapi.server.species.dto.SpeciesRestBeanParams
import com.cezarykluczynski.stapi.server.species.dto.SpeciesV2RestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class SpeciesBaseRestMapperTest extends AbstractSpeciesMapperTest {

	private SpeciesBaseRestMapper speciesBaseRestMapper

	void setup() {
		speciesBaseRestMapper = Mappers.getMapper(SpeciesBaseRestMapper)
	}

	void "maps SpeciesRestBeanParams to SpeciesRequestDTO"() {
		given:
		SpeciesRestBeanParams speciesRestBeanParams = new SpeciesRestBeanParams(
				uid: UID,
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
		SpeciesRequestDTO speciesRequestDTO = speciesBaseRestMapper.mapBase speciesRestBeanParams

		then:
		speciesRequestDTO.uid == UID
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

	void "maps SpeciesV2RestBeanParams to SpeciesRequestDTO"() {
		given:
		SpeciesV2RestBeanParams speciesRestBeanParams = new SpeciesV2RestBeanParams(
				uid: UID,
				name: NAME,
				extinctSpecies: EXTINCT_SPECIES,
				warpCapableSpecies: WARP_CAPABLE_SPECIES,
				extraGalacticSpecies: EXTRA_GALACTIC_SPECIES,
				humanoidSpecies: HUMANOID_SPECIES,
				reptilianSpecies: REPTILIAN_SPECIES,
				avianSpecies: AVIAN_SPECIES,
				nonCorporealSpecies: NON_CORPOREAL_SPECIES,
				shapeshiftingSpecies: SHAPESHIFTING_SPECIES,
				spaceborneSpecies: SPACEBORNE_SPECIES,
				telepathicSpecies: TELEPATHIC_SPECIES,
				transDimensionalSpecies: TRANS_DIMENSIONAL_SPECIES,
				unnamedSpecies: UNNAMED_SPECIES,
				alternateReality: ALTERNATE_REALITY)

		when:
		SpeciesRequestDTO speciesRequestDTO = speciesBaseRestMapper.mapV2Base speciesRestBeanParams

		then:
		speciesRequestDTO.uid == UID
		speciesRequestDTO.name == NAME
		speciesRequestDTO.extinctSpecies == EXTINCT_SPECIES
		speciesRequestDTO.warpCapableSpecies == WARP_CAPABLE_SPECIES
		speciesRequestDTO.extraGalacticSpecies == EXTRA_GALACTIC_SPECIES
		speciesRequestDTO.humanoidSpecies == HUMANOID_SPECIES
		speciesRequestDTO.reptilianSpecies == REPTILIAN_SPECIES
		speciesRequestDTO.avianSpecies == AVIAN_SPECIES
		speciesRequestDTO.nonCorporealSpecies == NON_CORPOREAL_SPECIES
		speciesRequestDTO.shapeshiftingSpecies == SHAPESHIFTING_SPECIES
		speciesRequestDTO.spaceborneSpecies == SPACEBORNE_SPECIES
		speciesRequestDTO.telepathicSpecies == TELEPATHIC_SPECIES
		speciesRequestDTO.transDimensionalSpecies == TRANS_DIMENSIONAL_SPECIES
		speciesRequestDTO.unnamedSpecies == UNNAMED_SPECIES
		speciesRequestDTO.alternateReality == ALTERNATE_REALITY
	}

	void "maps DB entity to base REST entity"() {
		given:
		Species species = createSpecies()

		when:
		SpeciesBase speciesBase = speciesBaseRestMapper.mapBase(Lists.newArrayList(species))[0]

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

	void "maps DB entity to base REST V2 entity"() {
		given:
		Species species = createSpecies()

		when:
		SpeciesV2Base speciesV2Base = speciesBaseRestMapper.mapV2Base(Lists.newArrayList(species))[0]

		then:
		speciesV2Base.name == NAME
		speciesV2Base.uid == UID
		speciesV2Base.homeworld != null
		speciesV2Base.quadrant != null
		speciesV2Base.extinctSpecies == EXTINCT_SPECIES
		speciesV2Base.warpCapableSpecies == WARP_CAPABLE_SPECIES
		speciesV2Base.extraGalacticSpecies == EXTRA_GALACTIC_SPECIES
		speciesV2Base.humanoidSpecies == HUMANOID_SPECIES
		speciesV2Base.reptilianSpecies == REPTILIAN_SPECIES
		speciesV2Base.avianSpecies == AVIAN_SPECIES
		speciesV2Base.nonCorporealSpecies == NON_CORPOREAL_SPECIES
		speciesV2Base.shapeshiftingSpecies == SHAPESHIFTING_SPECIES
		speciesV2Base.spaceborneSpecies == SPACEBORNE_SPECIES
		speciesV2Base.telepathicSpecies == TELEPATHIC_SPECIES
		speciesV2Base.transDimensionalSpecies == TRANS_DIMENSIONAL_SPECIES
		speciesV2Base.unnamedSpecies == UNNAMED_SPECIES
		speciesV2Base.alternateReality == ALTERNATE_REALITY
	}

}
