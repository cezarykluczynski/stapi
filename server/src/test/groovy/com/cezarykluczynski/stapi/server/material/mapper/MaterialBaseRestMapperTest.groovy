package com.cezarykluczynski.stapi.server.material.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.MaterialBase
import com.cezarykluczynski.stapi.model.material.dto.MaterialRequestDTO
import com.cezarykluczynski.stapi.model.material.entity.Material
import com.cezarykluczynski.stapi.server.material.dto.MaterialRestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class MaterialBaseRestMapperTest extends AbstractMaterialMapperTest {

	private MaterialBaseRestMapper materialBaseRestMapper

	void setup() {
		materialBaseRestMapper = Mappers.getMapper(MaterialBaseRestMapper)
	}

	void "maps MaterialRestBeanParams to MaterialRequestDTO"() {
		given:
		MaterialRestBeanParams materialRestBeanParams = new MaterialRestBeanParams(
				uid: UID,
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

		when:
		MaterialRequestDTO materialRequestDTO = materialBaseRestMapper.mapBase materialRestBeanParams

		then:
		materialRequestDTO.uid == UID
		materialRequestDTO.name == NAME
		materialRequestDTO.chemicalCompound == CHEMICAL_COMPOUND
		materialRequestDTO.biochemicalCompound == BIOCHEMICAL_COMPOUND
		materialRequestDTO.drug == DRUG
		materialRequestDTO.poisonousSubstance == POISONOUS_SUBSTANCE
		materialRequestDTO.explosive == EXPLOSIVE
		materialRequestDTO.gemstone == GEMSTONE
		materialRequestDTO.alloyOrComposite == ALLOY_OR_COMPOSITE
		materialRequestDTO.fuel == FUEL
		materialRequestDTO.mineral == MINERAL
		materialRequestDTO.preciousMaterial == PRECIOUS_MATERIAL
	}

	void "maps DB entity to base REST entity"() {
		given:
		Material material = createMaterial()

		when:
		MaterialBase materialBase = materialBaseRestMapper.mapBase(Lists.newArrayList(material))[0]

		then:
		materialBase.uid == UID
		materialBase.name == NAME
		materialBase.chemicalCompound == CHEMICAL_COMPOUND
		materialBase.biochemicalCompound == BIOCHEMICAL_COMPOUND
		materialBase.drug == DRUG
		materialBase.poisonousSubstance == POISONOUS_SUBSTANCE
		materialBase.explosive == EXPLOSIVE
		materialBase.gemstone == GEMSTONE
		materialBase.alloyOrComposite == ALLOY_OR_COMPOSITE
		materialBase.fuel == FUEL
		materialBase.mineral == MINERAL
		materialBase.preciousMaterial == PRECIOUS_MATERIAL
	}

}
