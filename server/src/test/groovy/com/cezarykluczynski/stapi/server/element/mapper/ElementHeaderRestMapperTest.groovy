package com.cezarykluczynski.stapi.server.element.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.ElementHeader
import com.cezarykluczynski.stapi.model.element.entity.Element
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class ElementHeaderRestMapperTest extends AbstractElementMapperTest {

	private ElementHeaderRestMapper elementHeaderRestMapper

	void setup() {
		elementHeaderRestMapper = Mappers.getMapper(ElementHeaderRestMapper)
	}

	void "maps DB entity to REST header"() {
		given:
		Element element = new Element(
				uid: UID,
				name: NAME)

		when:
		ElementHeader elementHeader = elementHeaderRestMapper.map(Lists.newArrayList(element))[0]

		then:
		elementHeader.uid == UID
		elementHeader.name == NAME
	}

}
