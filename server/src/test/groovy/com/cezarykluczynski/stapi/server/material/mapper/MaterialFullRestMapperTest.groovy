package com.cezarykluczynski.stapi.server.material.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.MaterialFull
import com.cezarykluczynski.stapi.model.material.entity.Material
import org.mapstruct.factory.Mappers

class MaterialFullRestMapperTest extends AbstractMaterialMapperTest {

	private MaterialFullRestMapper materialFullRestMapper

	void setup() {
		materialFullRestMapper = Mappers.getMapper(MaterialFullRestMapper)
	}

	void "maps DB entity to full REST entity"() {
		given:
		Material dBMaterial = createMaterial()

		when:
		MaterialFull materialFull = materialFullRestMapper.mapFull(dBMaterial)

		then:
		materialFull.uid == UID
		materialFull.name == NAME
		materialFull.chemicalCompound == CHEMICAL_COMPOUND
		materialFull.biochemicalCompound == BIOCHEMICAL_COMPOUND
		materialFull.drug == DRUG
		materialFull.poisonousSubstance == POISONOUS_SUBSTANCE
		materialFull.explosive == EXPLOSIVE
		materialFull.gemstone == GEMSTONE
		materialFull.alloyOrComposite == ALLOY_OR_COMPOSITE
		materialFull.fuel == FUEL
		materialFull.mineral == MINERAL
		materialFull.preciousMaterial == PRECIOUS_MATERIAL
	}

}
