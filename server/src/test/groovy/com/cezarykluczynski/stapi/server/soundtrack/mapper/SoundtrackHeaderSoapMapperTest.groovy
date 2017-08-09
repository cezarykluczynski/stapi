package com.cezarykluczynski.stapi.server.soundtrack.mapper

import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackHeader
import com.cezarykluczynski.stapi.model.soundtrack.entity.Soundtrack
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class SoundtrackHeaderSoapMapperTest extends AbstractSoundtrackMapperTest {

	private SoundtrackHeaderSoapMapper soundtrackHeaderSoapMapper

	void setup() {
		soundtrackHeaderSoapMapper = Mappers.getMapper(SoundtrackHeaderSoapMapper)
	}

	void "maps DB entity to SOAP header"() {
		given:
		Soundtrack soundtrack = new Soundtrack(
				uid: UID,
				title: TITLE)

		when:
		SoundtrackHeader soundtrackHeader = soundtrackHeaderSoapMapper.map(Lists.newArrayList(soundtrack))[0]

		then:
		soundtrackHeader.uid == UID
		soundtrackHeader.title == TITLE
	}

}
