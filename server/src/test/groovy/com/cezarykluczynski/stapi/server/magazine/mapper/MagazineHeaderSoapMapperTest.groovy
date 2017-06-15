package com.cezarykluczynski.stapi.server.magazine.mapper

import com.cezarykluczynski.stapi.client.v1.soap.MagazineHeader
import com.cezarykluczynski.stapi.model.magazine.entity.Magazine
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class MagazineHeaderSoapMapperTest extends AbstractMagazineMapperTest {

	private MagazineHeaderSoapMapper magazineHeaderSoapMapper

	void setup() {
		magazineHeaderSoapMapper = Mappers.getMapper(MagazineHeaderSoapMapper)
	}

	void "maps DB entity to SOAP header"() {
		given:
		Magazine magazine = new Magazine(
				uid: UID,
				title: TITLE)

		when:
		MagazineHeader magazineHeader = magazineHeaderSoapMapper.map(Lists.newArrayList(magazine))[0]

		then:
		magazineHeader.uid == UID
		magazineHeader.title == TITLE
	}

}
