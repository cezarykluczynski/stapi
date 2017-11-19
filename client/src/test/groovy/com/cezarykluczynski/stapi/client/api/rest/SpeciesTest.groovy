package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.SpeciesApi
import com.cezarykluczynski.stapi.client.v1.rest.model.SpeciesBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.SpeciesFullResponse
import com.cezarykluczynski.stapi.util.AbstractSpeciesTest

class SpeciesTest extends AbstractSpeciesTest {

	private SpeciesApi speciesApiMock

	private Species species

	void setup() {
		speciesApiMock = Mock()
		species = new Species(speciesApiMock, API_KEY)
	}

	void "gets single entity"() {
		given:
		SpeciesFullResponse speciesFullResponse = Mock()

		when:
		SpeciesFullResponse speciesFullResponseOutput = species.get(UID)

		then:
		1 * speciesApiMock.speciesGet(UID, API_KEY) >> speciesFullResponse
		0 * _
		speciesFullResponse == speciesFullResponseOutput
	}

	void "searches entities"() {
		given:
		SpeciesBaseResponse speciesBaseResponse = Mock()

		when:
		SpeciesBaseResponse speciesBaseResponseOutput = species.search(PAGE_NUMBER, PAGE_SIZE, SORT, NAME, EXTINCT_SPECIES, WARP_CAPABLE_SPECIES,
				EXTRA_GALACTIC_SPECIES, HUMANOID_SPECIES, REPTILIAN_SPECIES, NON_CORPOREAL_SPECIES, SHAPESHIFTING_SPECIES, SPACEBORNE_SPECIES,
				TELEPATHIC_SPECIES, TRANS_DIMENSIONAL_SPECIES, UNNAMED_SPECIES, ALTERNATE_REALITY)

		then:
		1 * speciesApiMock.speciesSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, API_KEY, NAME, EXTINCT_SPECIES, WARP_CAPABLE_SPECIES,
				EXTRA_GALACTIC_SPECIES, HUMANOID_SPECIES, REPTILIAN_SPECIES, NON_CORPOREAL_SPECIES, SHAPESHIFTING_SPECIES, SPACEBORNE_SPECIES,
				TELEPATHIC_SPECIES, TRANS_DIMENSIONAL_SPECIES, UNNAMED_SPECIES, ALTERNATE_REALITY) >> speciesBaseResponse
		0 * _
		speciesBaseResponse == speciesBaseResponseOutput
	}

}
