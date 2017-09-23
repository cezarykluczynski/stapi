package com.cezarykluczynski.stapi.server.title.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.TitleHeader
import com.cezarykluczynski.stapi.model.title.entity.Title
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class TitleHeaderRestMapperTest extends AbstractTitleMapperTest {

	private TitleHeaderRestMapper titleHeaderRestMapper

	void setup() {
		titleHeaderRestMapper = Mappers.getMapper(TitleHeaderRestMapper)
	}

	void "maps DB entity to REST header"() {
		given:
		Title title = new Title(
				uid: UID,
				name: NAME)

		when:
		TitleHeader titleHeader = titleHeaderRestMapper.map(Lists.newArrayList(title))[0]

		then:
		titleHeader.uid == UID
		titleHeader.name == NAME
	}

}
