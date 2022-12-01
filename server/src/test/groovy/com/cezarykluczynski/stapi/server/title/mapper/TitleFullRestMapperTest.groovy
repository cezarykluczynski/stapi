package com.cezarykluczynski.stapi.server.title.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.TitleFull
import com.cezarykluczynski.stapi.client.v1.rest.model.TitleV2Full
import com.cezarykluczynski.stapi.model.title.entity.Title
import org.mapstruct.factory.Mappers

class TitleFullRestMapperTest extends AbstractTitleMapperTest {

	private TitleFullRestMapper titleFullRestMapper

	void setup() {
		titleFullRestMapper = Mappers.getMapper(TitleFullRestMapper)
	}

	void "maps DB entity to full REST entity"() {
		given:
		Title title = createTitle()

		when:
		TitleFull titleFull = titleFullRestMapper.mapFull(title)

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

	void "maps DB entity to full REST V2 entity"() {
		given:
		Title title = createTitle()

		when:
		TitleV2Full titleFull = titleFullRestMapper.mapV2Full(title)

		then:
		titleFull.uid == UID
		titleFull.name == NAME
		titleFull.militaryRank == MILITARY_RANK
		titleFull.fleetRank == FLEET_RANK
		titleFull.religiousTitle == RELIGIOUS_TITLE
		titleFull.educationTitle == EDUCATION_TITLE
		titleFull.position == POSITION
		titleFull.mirror == MIRROR
		titleFull.characters.size() == title.characters.size()
	}

}
