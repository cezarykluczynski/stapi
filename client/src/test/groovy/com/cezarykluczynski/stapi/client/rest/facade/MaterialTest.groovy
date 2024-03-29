package com.cezarykluczynski.stapi.client.rest.facade

import static AbstractFacadeTest.SORT
import static AbstractFacadeTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.rest.api.MaterialApi
import com.cezarykluczynski.stapi.client.rest.model.MaterialBaseResponse
import com.cezarykluczynski.stapi.client.rest.model.MaterialFullResponse
import com.cezarykluczynski.stapi.client.rest.model.MaterialSearchCriteria
import com.cezarykluczynski.stapi.util.AbstractMaterialTest

class MaterialTest extends AbstractMaterialTest {

	private MaterialApi materialApiMock

	private Material material

	void setup() {
		materialApiMock = Mock()
		material = new Material(materialApiMock)
	}

	void "gets single entity"() {
		given:
		MaterialFullResponse materialFullResponse = Mock()

		when:
		MaterialFullResponse materialFullResponseOutput = material.get(UID)

		then:
		1 * materialApiMock.v1GetMaterial(UID) >> materialFullResponse
		0 * _
		materialFullResponse == materialFullResponseOutput
	}

	void "searches entities with criteria"() {
		given:
		MaterialBaseResponse materialBaseResponse = Mock()
		MaterialSearchCriteria materialSearchCriteria = new MaterialSearchCriteria(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				name: NAME,
				chemicalCompound: CHEMICAL_COMPOUND,
				biochemicalCompound: BIOCHEMICAL_COMPOUND,
				drug: DRUG,
				poisonousSubstance: POISONOUS_SUBSTANCE,
				explosive: EXPLOSIVE,
				gemstone: GEMSTONE,
				alloyOrComposite: ALLOY_OR_COMPOSITE,
				fuel: FUEL,
				mineral: MINERAL,
				preciousMaterial: PRECIOUS_MATERIAL)
		materialSearchCriteria.sort = SORT

		when:
		MaterialBaseResponse materialBaseResponseOutput = material.search(materialSearchCriteria)

		then:
		1 * materialApiMock.v1SearchMaterials(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, CHEMICAL_COMPOUND, BIOCHEMICAL_COMPOUND,
				DRUG, POISONOUS_SUBSTANCE, EXPLOSIVE, GEMSTONE, ALLOY_OR_COMPOSITE, FUEL, MINERAL, PRECIOUS_MATERIAL) >> materialBaseResponse
		0 * _
		materialBaseResponse == materialBaseResponseOutput
	}

}
