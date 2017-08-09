package com.cezarykluczynski.stapi.server.soundtrack.mapper

import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackFull
import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackFullRequest
import com.cezarykluczynski.stapi.model.soundtrack.dto.SoundtrackRequestDTO
import com.cezarykluczynski.stapi.model.soundtrack.entity.Soundtrack
import org.mapstruct.factory.Mappers

class SoundtrackFullSoapMapperTest extends AbstractSoundtrackMapperTest {

	private SoundtrackFullSoapMapper soundtrackFullSoapMapper

	void setup() {
		soundtrackFullSoapMapper = Mappers.getMapper(SoundtrackFullSoapMapper)
	}

	void "maps SOAP SoundtrackFullRequest to SoundtrackBaseRequestDTO"() {
		given:
		SoundtrackFullRequest soundtrackFullRequest = new SoundtrackFullRequest(uid: UID)

		when:
		SoundtrackRequestDTO soundtrackRequestDTO = soundtrackFullSoapMapper.mapFull soundtrackFullRequest

		then:
		soundtrackRequestDTO.uid == UID
	}

	void "maps DB entity to full SOAP entity"() {
		given:
		Soundtrack soundtrack = createSoundtrack()

		when:
		SoundtrackFull soundtrackFull = soundtrackFullSoapMapper.mapFull(soundtrack)

		then:
		soundtrackFull.uid == UID
		soundtrackFull.title == TITLE
		soundtrackFull.releaseDate == RELEASE_DATE_XML
		soundtrackFull.length == LENGTH
		soundtrackFull.labels.size() == soundtrack.labels.size()
		soundtrackFull.composers.size() == soundtrack.composers.size()
		soundtrackFull.contributors.size() == soundtrack.contributors.size()
		soundtrackFull.orchestrators.size() == soundtrack.orchestrators.size()
		soundtrackFull.references.size() == soundtrack.references.size()
	}

}
