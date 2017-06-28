package com.cezarykluczynski.stapi.server.season.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.SeasonHeader
import com.cezarykluczynski.stapi.model.season.entity.Season
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class SeasonHeaderRestMapperTest extends AbstractSeasonMapperTest {

	private SeasonHeaderRestMapper seasonHeaderRestMapper

	void setup() {
		seasonHeaderRestMapper = Mappers.getMapper(SeasonHeaderRestMapper)
	}

	void "maps DB entity to REST header"() {
		given:
		Season season = new Season(
				uid: UID,
				title: TITLE)

		when:
		SeasonHeader seasonHeader = seasonHeaderRestMapper.map(Lists.newArrayList(season))[0]

		then:
		seasonHeader.uid == UID
		seasonHeader.title == TITLE
	}

}
