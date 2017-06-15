package com.cezarykluczynski.stapi.server.magazine.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineHeader
import com.cezarykluczynski.stapi.model.magazine.entity.Magazine
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class MagazineHeaderRestMapperTest extends AbstractMagazineMapperTest {

	private MagazineHeaderRestMapper magazineHeaderRestMapper

	void setup() {
		magazineHeaderRestMapper = Mappers.getMapper(MagazineHeaderRestMapper)
	}

	void "maps DB entity to REST header"() {
		given:
		Magazine magazine = new Magazine(
				uid: UID,
				title: TITLE)

		when:
		MagazineHeader magazineHeader = magazineHeaderRestMapper.map(Lists.newArrayList(magazine))[0]

		then:
		magazineHeader.uid == UID
		magazineHeader.title == TITLE
	}

}
