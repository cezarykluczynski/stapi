package com.cezarykluczynski.stapi.server.material.mapper

import com.cezarykluczynski.stapi.client.v1.soap.MaterialFull
import com.cezarykluczynski.stapi.client.v1.soap.MaterialFullRequest
import com.cezarykluczynski.stapi.model.material.dto.MaterialRequestDTO
import com.cezarykluczynski.stapi.model.material.entity.Material
import org.mapstruct.factory.Mappers

class MaterialFullSoapMapperTest extends AbstractMaterialMapperTest {

	private MaterialFullSoapMapper materialFullSoapMapper

	void setup() {
		materialFullSoapMapper = Mappers.getMapper(MaterialFullSoapMapper)
	}

	void "maps SOAP MaterialFullRequest to MaterialBaseRequestDTO"() {
		given:
		MaterialFullRequest materialFullRequest = new MaterialFullRequest(uid: UID)

		when:
		MaterialRequestDTO materialRequestDTO = materialFullSoapMapper.mapFull materialFullRequest

		then:
		materialRequestDTO.uid == UID
	}

	void "maps DB entity to full SOAP entity"() {
		given:
		Material material = createMaterial()

		when:
		MaterialFull materialFull = materialFullSoapMapper.mapFull(material)

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
