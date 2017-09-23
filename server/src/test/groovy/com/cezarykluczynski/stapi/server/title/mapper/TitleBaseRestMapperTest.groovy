package com.cezarykluczynski.stapi.server.title.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.TitleBase
import com.cezarykluczynski.stapi.model.title.dto.TitleRequestDTO
import com.cezarykluczynski.stapi.model.title.entity.Title
import com.cezarykluczynski.stapi.server.title.dto.TitleRestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class TitleBaseRestMapperTest extends AbstractTitleMapperTest {

	private TitleBaseRestMapper titleBaseRestMapper

	void setup() {
		titleBaseRestMapper = Mappers.getMapper(TitleBaseRestMapper)
	}

	void "maps TitleRestBeanParams to TitleRequestDTO"() {
		given:
		TitleRestBeanParams titleRestBeanParams = new TitleRestBeanParams(
				name: NAME,
				militaryRank: MILITARY_RANK,
				fleetRank: FLEET_RANK,
				religiousTitle: RELIGIOUS_TITLE,
				position: POSITION,
				mirror: MIRROR)

		when:
		TitleRequestDTO titleRequestDTO = titleBaseRestMapper.mapBase titleRestBeanParams

		then:
		titleRequestDTO.name == NAME
		titleRequestDTO.militaryRank == MILITARY_RANK
		titleRequestDTO.fleetRank == FLEET_RANK
		titleRequestDTO.religiousTitle == RELIGIOUS_TITLE
		titleRequestDTO.position == POSITION
		titleRequestDTO.mirror == MIRROR
	}

	void "maps DB entity to base REST entity"() {
		given:
		Title title = createTitle()

		when:
		TitleBase titleBase = titleBaseRestMapper.mapBase(Lists.newArrayList(title))[0]

		then:
		titleBase.uid == UID
		titleBase.name == NAME
		titleBase.militaryRank == MILITARY_RANK
		titleBase.fleetRank == FLEET_RANK
		titleBase.religiousTitle == RELIGIOUS_TITLE
		titleBase.position == POSITION
		titleBase.mirror == MIRROR
	}

}
