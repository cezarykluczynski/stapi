package com.cezarykluczynski.stapi.server.species.mapper

import com.cezarykluczynski.stapi.client.v1.soap.SpeciesHeader
import com.cezarykluczynski.stapi.model.species.entity.Species
import com.cezarykluczynski.stapi.server.common.mapper.AbstractRealWorldPersonMapperTest
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class SpeciesHeaderSoapMapperTest extends AbstractRealWorldPersonMapperTest {

	private SpeciesHeaderSoapMapper speciesHeaderSoapMapper

	void setup() {
		speciesHeaderSoapMapper = Mappers.getMapper(SpeciesHeaderSoapMapper)
	}

	void "maps DB entity to SOAP header"() {
		given:
		Species species = new Species(
				name: NAME,
				guid: GUID)

		when:
		SpeciesHeader speciesHeader = speciesHeaderSoapMapper.map(Lists.newArrayList(species))[0]

		then:
		speciesHeader.name == NAME
		speciesHeader.guid == GUID
	}

}
