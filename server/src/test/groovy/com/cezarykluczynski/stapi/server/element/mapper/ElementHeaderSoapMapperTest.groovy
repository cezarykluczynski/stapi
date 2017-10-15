package com.cezarykluczynski.stapi.server.element.mapper

import com.cezarykluczynski.stapi.client.v1.soap.ElementHeader
import com.cezarykluczynski.stapi.model.element.entity.Element
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class ElementHeaderSoapMapperTest extends AbstractElementMapperTest {

	private ElementHeaderSoapMapper elementHeaderSoapMapper

	void setup() {
		elementHeaderSoapMapper = Mappers.getMapper(ElementHeaderSoapMapper)
	}

	void "maps DB entity to SOAP header"() {
		given:
		Element element = new Element(
				uid: UID,
				name: NAME)

		when:
		ElementHeader elementHeader = elementHeaderSoapMapper.map(Lists.newArrayList(element))[0]

		then:
		elementHeader.uid == UID
		elementHeader.name == NAME
	}

}
