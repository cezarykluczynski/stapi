package com.cezarykluczynski.stapi.client.rest.facade

import static AbstractFacadeTest.SORT
import static AbstractFacadeTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.rest.api.SpeciesApi
import com.cezarykluczynski.stapi.client.rest.model.SpeciesV2BaseResponse
import com.cezarykluczynski.stapi.client.rest.model.SpeciesV2FullResponse
import com.cezarykluczynski.stapi.client.rest.model.SpeciesV2SearchCriteria
import com.cezarykluczynski.stapi.util.AbstractSpeciesTest

class SpeciesTest extends AbstractSpeciesTest {

	private SpeciesApi speciesApiMock

	private Species species

	void setup() {
		speciesApiMock = Mock()
		species = new Species(speciesApiMock)
	}

	void "gets single entity (V2)"() {
		given:
		SpeciesV2FullResponse speciesV2FullResponse = Mock()

		when:
		SpeciesV2FullResponse speciesV2FullResponseOutput = species.getV2(UID)

		then:
		1 * speciesApiMock.v2GetSpecies(UID) >> speciesV2FullResponse
		0 * _
		speciesV2FullResponse == speciesV2FullResponseOutput
	}

	void "searches entities with criteria (V2)"() {
		given:
		SpeciesV2BaseResponse speciesV2BaseResponse = Mock()
		SpeciesV2SearchCriteria speciesV2SearchCriteria = new SpeciesV2SearchCriteria(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
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
		speciesV2SearchCriteria.sort = SORT

		when:
		SpeciesV2BaseResponse speciesV2BaseResponseOutput = species.searchV2(speciesV2SearchCriteria)

		then:
		1 * speciesApiMock.v2SearchSpecies(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, EXTINCT_SPECIES, WARP_CAPABLE_SPECIES,
				EXTRA_GALACTIC_SPECIES, HUMANOID_SPECIES, REPTILIAN_SPECIES, AVIAN_SPECIES, NON_CORPOREAL_SPECIES, SHAPESHIFTING_SPECIES,
				SPACEBORNE_SPECIES, TELEPATHIC_SPECIES, TRANS_DIMENSIONAL_SPECIES, UNNAMED_SPECIES, ALTERNATE_REALITY) >> speciesV2BaseResponse
		0 * _
		speciesV2BaseResponse == speciesV2BaseResponseOutput
	}

}
