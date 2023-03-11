package com.cezarykluczynski.stapi.client.api.rest

import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT
import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.api.dto.SpeciesV2SearchCriteria
import com.cezarykluczynski.stapi.client.v1.rest.api.SpeciesApi
import com.cezarykluczynski.stapi.client.v1.rest.model.SpeciesBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.SpeciesFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.SpeciesV2BaseResponse
import com.cezarykluczynski.stapi.util.AbstractSpeciesTest

class SpeciesTest extends AbstractSpeciesTest {

	private SpeciesApi speciesApiMock

	private Species species

	void setup() {
		speciesApiMock = Mock()
		species = new Species(speciesApiMock)
	}

	void "gets single entity"() {
		given:
		SpeciesFullResponse speciesFullResponse = Mock()

		when:
		SpeciesFullResponse speciesFullResponseOutput = species.get(UID)

		then:
		1 * speciesApiMock.v1RestSpeciesGet(UID) >> speciesFullResponse
		0 * _
		speciesFullResponse == speciesFullResponseOutput
	}

	void "gets single entity (V2)"() {
		given:
		SpeciesFullResponse speciesFullResponse = Mock()

		when:
		SpeciesFullResponse speciesFullResponseOutput = species.get(UID)

		then:
		1 * speciesApiMock.v1RestSpeciesGet(UID) >> speciesFullResponse
		0 * _
		speciesFullResponse == speciesFullResponseOutput
	}

	void "searches entities"() {
		given:
		SpeciesBaseResponse speciesBaseResponse = Mock()

		when:
		SpeciesBaseResponse speciesBaseResponseOutput = species.search(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, EXTINCT_SPECIES, WARP_CAPABLE_SPECIES,
				EXTRA_GALACTIC_SPECIES, HUMANOID_SPECIES, REPTILIAN_SPECIES, NON_CORPOREAL_SPECIES, SHAPESHIFTING_SPECIES, SPACEBORNE_SPECIES,
				TELEPATHIC_SPECIES, TRANS_DIMENSIONAL_SPECIES, UNNAMED_SPECIES, ALTERNATE_REALITY)

		then:
		1 * speciesApiMock.v1RestSpeciesSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, EXTINCT_SPECIES, WARP_CAPABLE_SPECIES,
				EXTRA_GALACTIC_SPECIES, HUMANOID_SPECIES, REPTILIAN_SPECIES, NON_CORPOREAL_SPECIES, SHAPESHIFTING_SPECIES, SPACEBORNE_SPECIES,
				TELEPATHIC_SPECIES, TRANS_DIMENSIONAL_SPECIES, UNNAMED_SPECIES, ALTERNATE_REALITY) >> speciesBaseResponse
		0 * _
		speciesBaseResponse == speciesBaseResponseOutput
	}

	void "searches entities (V2)"() {
		given:
		SpeciesV2BaseResponse speciesV2BaseResponse = Mock()

		when:
		SpeciesV2BaseResponse speciesV2BaseResponseOutput = species.searchV2(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, EXTINCT_SPECIES,
				WARP_CAPABLE_SPECIES, EXTRA_GALACTIC_SPECIES, HUMANOID_SPECIES, REPTILIAN_SPECIES, AVIAN_SPECIES, NON_CORPOREAL_SPECIES,
				SHAPESHIFTING_SPECIES, SPACEBORNE_SPECIES, TELEPATHIC_SPECIES, TRANS_DIMENSIONAL_SPECIES, UNNAMED_SPECIES, ALTERNATE_REALITY)

		then:
		1 * speciesApiMock.v2RestSpeciesSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, EXTINCT_SPECIES, WARP_CAPABLE_SPECIES,
				EXTRA_GALACTIC_SPECIES, HUMANOID_SPECIES, REPTILIAN_SPECIES, AVIAN_SPECIES, NON_CORPOREAL_SPECIES, SHAPESHIFTING_SPECIES,
				SPACEBORNE_SPECIES, TELEPATHIC_SPECIES, TRANS_DIMENSIONAL_SPECIES, UNNAMED_SPECIES, ALTERNATE_REALITY) >> speciesV2BaseResponse
		0 * _
		speciesV2BaseResponse == speciesV2BaseResponseOutput
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
		speciesV2SearchCriteria.sort.addAll(SORT)

		when:
		SpeciesV2BaseResponse speciesV2BaseResponseOutput = species.searchV2(speciesV2SearchCriteria)

		then:
		1 * speciesApiMock.v2RestSpeciesSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, EXTINCT_SPECIES, WARP_CAPABLE_SPECIES,
				EXTRA_GALACTIC_SPECIES, HUMANOID_SPECIES, REPTILIAN_SPECIES, AVIAN_SPECIES, NON_CORPOREAL_SPECIES, SHAPESHIFTING_SPECIES,
				SPACEBORNE_SPECIES, TELEPATHIC_SPECIES, TRANS_DIMENSIONAL_SPECIES, UNNAMED_SPECIES, ALTERNATE_REALITY) >> speciesV2BaseResponse
		0 * _
		speciesV2BaseResponse == speciesV2BaseResponseOutput
	}

}
