package com.cezarykluczynski.stapi.server.species.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.SpeciesHeader
import com.cezarykluczynski.stapi.model.species.entity.Species
import com.cezarykluczynski.stapi.server.common.mapper.AbstractRealWorldPersonMapperTest
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class SpeciesHeaderRestMapperTest extends AbstractRealWorldPersonMapperTest {

	private SpeciesHeaderRestMapper speciesHeaderRestMapper

	void setup() {
		speciesHeaderRestMapper = Mappers.getMapper(SpeciesHeaderRestMapper)
	}

	void "maps DB entity to SOAP header"() {
		given:
		Species species = new Species(
				name: NAME,
				uid: UID)

		when:
		SpeciesHeader speciesHeader = speciesHeaderRestMapper.map(Lists.newArrayList(species))[0]

		then:
		speciesHeader.name == NAME
		speciesHeader.uid == UID
	}

}
