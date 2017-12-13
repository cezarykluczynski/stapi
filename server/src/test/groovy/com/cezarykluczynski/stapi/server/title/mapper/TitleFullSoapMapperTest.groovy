package com.cezarykluczynski.stapi.server.title.mapper

import com.cezarykluczynski.stapi.client.v1.soap.TitleFull
import com.cezarykluczynski.stapi.client.v1.soap.TitleFullRequest
import com.cezarykluczynski.stapi.model.title.dto.TitleRequestDTO
import com.cezarykluczynski.stapi.model.title.entity.Title
import org.mapstruct.factory.Mappers

class TitleFullSoapMapperTest extends AbstractTitleMapperTest {

	private TitleFullSoapMapper titleFullSoapMapper

	void setup() {
		titleFullSoapMapper = Mappers.getMapper(TitleFullSoapMapper)
	}

	void "maps SOAP TitleFullRequest to TitleBaseRequestDTO"() {
		given:
		TitleFullRequest titleFullRequest = new TitleFullRequest(uid: UID)

		when:
		TitleRequestDTO titleRequestDTO = titleFullSoapMapper.mapFull titleFullRequest

		then:
		titleRequestDTO.uid == UID
	}

	void "maps DB entity to full SOAP entity"() {
		given:
		Title title = createTitle()

		when:
		TitleFull titleFull = titleFullSoapMapper.mapFull(title)

		then:
		titleFull.uid == UID
		titleFull.name == NAME
		titleFull.militaryRank == MILITARY_RANK
		titleFull.fleetRank == FLEET_RANK
		titleFull.religiousTitle == RELIGIOUS_TITLE
		titleFull.position == POSITION
		titleFull.mirror == MIRROR
		titleFull.characters.size() == title.characters.size()
	}

}
