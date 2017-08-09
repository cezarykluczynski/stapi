package com.cezarykluczynski.stapi.server.soundtrack.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.SoundtrackFull
import com.cezarykluczynski.stapi.model.soundtrack.entity.Soundtrack
import org.mapstruct.factory.Mappers

class SoundtrackFullRestMapperTest extends AbstractSoundtrackMapperTest {

	private SoundtrackFullRestMapper soundtrackFullRestMapper

	void setup() {
		soundtrackFullRestMapper = Mappers.getMapper(SoundtrackFullRestMapper)
	}

	void "maps DB entity to full REST entity"() {
		given:
		Soundtrack soundtrack = createSoundtrack()

		when:
		SoundtrackFull soundtrackFull = soundtrackFullRestMapper.mapFull(soundtrack)

		then:
		soundtrackFull.uid == UID
		soundtrackFull.title == TITLE
		soundtrackFull.releaseDate == RELEASE_DATE
		soundtrackFull.length == LENGTH
		soundtrackFull.labels.size() == soundtrack.labels.size()
		soundtrackFull.composers.size() == soundtrack.composers.size()
		soundtrackFull.contributors.size() == soundtrack.contributors.size()
		soundtrackFull.orchestrators.size() == soundtrack.orchestrators.size()
		soundtrackFull.references.size() == soundtrack.references.size()
	}
}
