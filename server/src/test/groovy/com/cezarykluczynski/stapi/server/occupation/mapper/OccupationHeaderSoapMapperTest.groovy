package com.cezarykluczynski.stapi.server.occupation.mapper

import com.cezarykluczynski.stapi.client.v1.soap.OccupationHeader
import com.cezarykluczynski.stapi.model.occupation.entity.Occupation
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class OccupationHeaderSoapMapperTest extends AbstractOccupationMapperTest {

	private OccupationHeaderSoapMapper occupationHeaderSoapMapper

	void setup() {
		occupationHeaderSoapMapper = Mappers.getMapper(OccupationHeaderSoapMapper)
	}

	void "maps DB entity to SOAP header"() {
		given:
		Occupation occupation = new Occupation(
				uid: UID,
				name: NAME)

		when:
		OccupationHeader occupationHeader = occupationHeaderSoapMapper.map(Lists.newArrayList(occupation))[0]

		then:
		occupationHeader.uid == UID
		occupationHeader.name == NAME
	}

}
