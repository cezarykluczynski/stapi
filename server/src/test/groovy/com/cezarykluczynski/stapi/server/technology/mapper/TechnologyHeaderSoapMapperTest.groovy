package com.cezarykluczynski.stapi.server.technology.mapper

import com.cezarykluczynski.stapi.client.v1.soap.TechnologyHeader
import com.cezarykluczynski.stapi.model.technology.entity.Technology
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class TechnologyHeaderSoapMapperTest extends AbstractTechnologyMapperTest {

	private TechnologyHeaderSoapMapper technologyHeaderSoapMapper

	void setup() {
		technologyHeaderSoapMapper = Mappers.getMapper(TechnologyHeaderSoapMapper)
	}

	void "maps DB entity to SOAP header"() {
		given:
		Technology technology = new Technology(
				uid: UID,
				name: NAME)

		when:
		TechnologyHeader technologyHeader = technologyHeaderSoapMapper.map(Lists.newArrayList(technology))[0]

		then:
		technologyHeader.uid == UID
		technologyHeader.name == NAME
	}

}
