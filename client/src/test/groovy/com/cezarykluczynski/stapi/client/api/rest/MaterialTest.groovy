package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.MaterialApi
import com.cezarykluczynski.stapi.client.v1.rest.model.MaterialBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.MaterialFullResponse
import com.cezarykluczynski.stapi.util.AbstractMaterialTest

class MaterialTest extends AbstractMaterialTest {

	private MaterialApi materialApiMock

	private Material material

	void setup() {
		materialApiMock = Mock()
		material = new Material(materialApiMock, API_KEY)
	}

	void "gets single entity"() {
		given:
		MaterialFullResponse materialFullResponse = Mock()

		when:
		MaterialFullResponse materialFullResponseOutput = material.get(UID)

		then:
		1 * materialApiMock.materialGet(UID, API_KEY) >> materialFullResponse
		0 * _
		materialFullResponse == materialFullResponseOutput
	}

	void "searches entities"() {
		given:
		MaterialBaseResponse materialBaseResponse = Mock()

		when:
		MaterialBaseResponse materialBaseResponseOutput = material.search(PAGE_NUMBER, PAGE_SIZE, SORT, NAME, CHEMICAL_COMPOUND, BIOCHEMICAL_COMPOUND,
				DRUG, POISONOUS_SUBSTANCE, EXPLOSIVE, GEMSTONE, ALLOY_OR_COMPOSITE, FUEL, MINERAL, PRECIOUS_MATERIAL)

		then:
		1 * materialApiMock.materialSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, API_KEY, NAME, CHEMICAL_COMPOUND, BIOCHEMICAL_COMPOUND, DRUG,
				POISONOUS_SUBSTANCE, EXPLOSIVE, GEMSTONE, ALLOY_OR_COMPOSITE, FUEL, MINERAL, PRECIOUS_MATERIAL) >> materialBaseResponse
		0 * _
		materialBaseResponse == materialBaseResponseOutput
	}

}
