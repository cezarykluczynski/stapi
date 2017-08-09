package com.cezarykluczynski.stapi.server.soundtrack.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.SoundtrackHeader
import com.cezarykluczynski.stapi.model.soundtrack.entity.Soundtrack
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class SoundtrackHeaderRestMapperTest extends AbstractSoundtrackMapperTest {

	private SoundtrackHeaderRestMapper soundtrackHeaderRestMapper

	void setup() {
		soundtrackHeaderRestMapper = Mappers.getMapper(SoundtrackHeaderRestMapper)
	}

	void "maps DB entity to REST header"() {
		given:
		Soundtrack soundtrack = new Soundtrack(
				uid: UID,
				title: TITLE)

		when:
		SoundtrackHeader soundtrackHeader = soundtrackHeaderRestMapper.map(Lists.newArrayList(soundtrack))[0]

		then:
		soundtrackHeader.uid == UID
		soundtrackHeader.title == TITLE
	}

}
