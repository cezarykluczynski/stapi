package com.cezarykluczynski.stapi.server.material.mapper

import com.cezarykluczynski.stapi.client.v1.soap.MaterialBase
import com.cezarykluczynski.stapi.client.v1.soap.MaterialBaseRequest
import com.cezarykluczynski.stapi.model.material.dto.MaterialRequestDTO
import com.cezarykluczynski.stapi.model.material.entity.Material
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class MaterialBaseSoapMapperTest extends AbstractMaterialMapperTest {

	private MaterialBaseSoapMapper materialBaseSoapMapper

	void setup() {
		materialBaseSoapMapper = Mappers.getMapper(MaterialBaseSoapMapper)
	}

	void "maps SOAP MaterialBaseRequest to MaterialRequestDTO"() {
		given:
		MaterialBaseRequest materialBaseRequest = new MaterialBaseRequest(
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
		MaterialRequestDTO materialRequestDTO = materialBaseSoapMapper.mapBase materialBaseRequest

		then:
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

	void "maps DB entity to base SOAP entity"() {
		given:
		Material material = createMaterial()

		when:
		MaterialBase materialBase = materialBaseSoapMapper.mapBase(Lists.newArrayList(material))[0]

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
