package com.cezarykluczynski.stapi.server.title.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.TitleFull
import com.cezarykluczynski.stapi.model.title.entity.Title
import org.mapstruct.factory.Mappers

class TitleFullRestMapperTest extends AbstractTitleMapperTest {

	private TitleFullRestMapper titleFullRestMapper

	void setup() {
		titleFullRestMapper = Mappers.getMapper(TitleFullRestMapper)
	}

	void "maps DB entity to full REST entity"() {
		given:
		Title dBTitle = createTitle()

		when:
		TitleFull titleFull = titleFullRestMapper.mapFull(dBTitle)

		then:
		titleFull.uid == UID
		titleFull.name == NAME
		titleFull.militaryRank == MILITARY_RANK
		titleFull.fleetRank == FLEET_RANK
		titleFull.religiousTitle == RELIGIOUS_TITLE
		titleFull.position == POSITION
		titleFull.mirror == MIRROR
	}

}
