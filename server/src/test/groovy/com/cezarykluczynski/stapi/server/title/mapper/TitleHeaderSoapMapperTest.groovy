package com.cezarykluczynski.stapi.server.title.mapper

import com.cezarykluczynski.stapi.client.v1.soap.TitleHeader
import com.cezarykluczynski.stapi.model.title.entity.Title
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class TitleHeaderSoapMapperTest extends AbstractTitleMapperTest {

	private TitleHeaderSoapMapper titleHeaderSoapMapper

	void setup() {
		titleHeaderSoapMapper = Mappers.getMapper(TitleHeaderSoapMapper)
	}

	void "maps DB entity to SOAP header"() {
		given:
		Title title = new Title(
				uid: UID,
				name: NAME)

		when:
		TitleHeader titleHeader = titleHeaderSoapMapper.map(Lists.newArrayList(title))[0]

		then:
		titleHeader.uid == UID
		titleHeader.name == NAME
	}

}
