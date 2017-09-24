package com.cezarykluczynski.stapi.server.material.mapper

import com.cezarykluczynski.stapi.model.material.entity.Material
import com.cezarykluczynski.stapi.util.AbstractMaterialTest

abstract class AbstractMaterialMapperTest extends AbstractMaterialTest {

	protected static Material createMaterial() {
		new Material(
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
	}

}
