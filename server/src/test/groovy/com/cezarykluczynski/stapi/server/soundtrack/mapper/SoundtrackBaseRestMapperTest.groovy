package com.cezarykluczynski.stapi.server.soundtrack.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.SoundtrackBase
import com.cezarykluczynski.stapi.model.soundtrack.dto.SoundtrackRequestDTO
import com.cezarykluczynski.stapi.model.soundtrack.entity.Soundtrack
import com.cezarykluczynski.stapi.server.soundtrack.dto.SoundtrackRestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class SoundtrackBaseRestMapperTest extends AbstractSoundtrackMapperTest {

	private SoundtrackBaseRestMapper soundtrackBaseRestMapper

	void setup() {
		soundtrackBaseRestMapper = Mappers.getMapper(SoundtrackBaseRestMapper)
	}

	void "maps SoundtrackRestBeanParams to SoundtrackRequestDTO"() {
		given:
		SoundtrackRestBeanParams soundtrackRestBeanParams = new SoundtrackRestBeanParams(
				uid: UID,
				title: TITLE,
				releaseDateFrom: RELEASE_DATE_FROM,
				releaseDateTo: RELEASE_DATE_TO,
				lengthFrom: LENGTH_FROM,
				lengthTo: LENGTH_TO)

		when:
		SoundtrackRequestDTO soundtrackRequestDTO = soundtrackBaseRestMapper.mapBase soundtrackRestBeanParams

		then:
		soundtrackRequestDTO.uid == UID
		soundtrackRequestDTO.title == TITLE
		soundtrackRequestDTO.releaseDateFrom == RELEASE_DATE_FROM
		soundtrackRequestDTO.releaseDateTo == RELEASE_DATE_TO
		soundtrackRequestDTO.lengthFrom == LENGTH_FROM
		soundtrackRequestDTO.lengthTo == LENGTH_TO
	}

	void "maps DB entity to base REST entity"() {
		given:
		Soundtrack soundtrack = createSoundtrack()

		when:
		SoundtrackBase soundtrackBase = soundtrackBaseRestMapper.mapBase(Lists.newArrayList(soundtrack))[0]

		then:
		soundtrackBase.uid == UID
		soundtrackBase.title == TITLE
		soundtrackBase.releaseDate == RELEASE_DATE
		soundtrackBase.length == LENGTH
	}

}
