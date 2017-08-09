package com.cezarykluczynski.stapi.server.soundtrack.mapper

import com.cezarykluczynski.stapi.client.v1.soap.DateRange
import com.cezarykluczynski.stapi.client.v1.soap.IntegerRange
import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackBase as SoundtrackBase
import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackBaseRequest
import com.cezarykluczynski.stapi.model.soundtrack.dto.SoundtrackRequestDTO
import com.cezarykluczynski.stapi.model.soundtrack.entity.Soundtrack
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class SoundtrackBaseSoapMapperTest extends AbstractSoundtrackMapperTest {

	private SoundtrackBaseSoapMapper soundtrackBaseSoapMapper

	void setup() {
		soundtrackBaseSoapMapper = Mappers.getMapper(SoundtrackBaseSoapMapper)
	}

	void "maps SOAP SoundtrackRequest to SoundtrackRequestDTO"() {
		given:
		SoundtrackBaseRequest soundtrackBaseRequest = new SoundtrackBaseRequest(
				title: TITLE,
				releaseDate: new DateRange(
						from: RELEASE_DATE_FROM_XML,
						to: RELEASE_DATE_TO_XML
				),
				length: new IntegerRange(
						from: LENGTH_FROM,
						to: LENGTH_TO
				))

		when:
		SoundtrackRequestDTO soundtrackRequestDTO = soundtrackBaseSoapMapper.mapBase soundtrackBaseRequest

		then:
		soundtrackRequestDTO.title == TITLE
		soundtrackRequestDTO.releaseDateFrom == RELEASE_DATE_FROM
		soundtrackRequestDTO.releaseDateTo == RELEASE_DATE_TO
		soundtrackRequestDTO.lengthFrom == LENGTH_FROM
		soundtrackRequestDTO.lengthTo == LENGTH_TO
	}

	void "maps DB entity to base SOAP entity"() {
		given:
		Soundtrack soundtrack = createSoundtrack()

		when:
		SoundtrackBase soundtrackBase = soundtrackBaseSoapMapper.mapBase(Lists.newArrayList(soundtrack))[0]

		then:
		soundtrackBase.uid == UID
		soundtrackBase.title == TITLE
		soundtrackBase.releaseDate == RELEASE_DATE_XML
		soundtrackBase.length == LENGTH
	}

}
