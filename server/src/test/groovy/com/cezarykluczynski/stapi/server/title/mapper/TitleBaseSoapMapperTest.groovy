package com.cezarykluczynski.stapi.server.title.mapper

import com.cezarykluczynski.stapi.client.v1.soap.TitleBase
import com.cezarykluczynski.stapi.client.v1.soap.TitleBaseRequest
import com.cezarykluczynski.stapi.model.title.dto.TitleRequestDTO
import com.cezarykluczynski.stapi.model.title.entity.Title
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class TitleBaseSoapMapperTest extends AbstractTitleMapperTest {

	private TitleBaseSoapMapper titleBaseSoapMapper

	void setup() {
		titleBaseSoapMapper = Mappers.getMapper(TitleBaseSoapMapper)
	}

	void "maps SOAP TitleBaseRequest to TitleRequestDTO"() {
		given:
		TitleBaseRequest titleBaseRequest = new TitleBaseRequest(
				name: NAME,
				militaryRank: MILITARY_RANK,
				fleetRank: FLEET_RANK,
				religiousTitle: RELIGIOUS_TITLE,
				position: POSITION,
				mirror: MIRROR)

		when:
		TitleRequestDTO titleRequestDTO = titleBaseSoapMapper.mapBase titleBaseRequest

		then:
		titleRequestDTO.name == NAME
		titleRequestDTO.militaryRank == MILITARY_RANK
		titleRequestDTO.fleetRank == FLEET_RANK
		titleRequestDTO.religiousTitle == RELIGIOUS_TITLE
		titleRequestDTO.position == POSITION
		titleRequestDTO.mirror == MIRROR
	}

	void "maps DB entity to base SOAP entity"() {
		given:
		Title title = createTitle()

		when:
		TitleBase titleBase = titleBaseSoapMapper.mapBase(Lists.newArrayList(title))[0]

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
