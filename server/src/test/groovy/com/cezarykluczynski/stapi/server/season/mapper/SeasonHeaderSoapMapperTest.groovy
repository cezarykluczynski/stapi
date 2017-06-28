package com.cezarykluczynski.stapi.server.season.mapper

import com.cezarykluczynski.stapi.client.v1.soap.SeasonHeader
import com.cezarykluczynski.stapi.model.season.entity.Season
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class SeasonHeaderSoapMapperTest extends AbstractSeasonMapperTest {

	private SeasonHeaderSoapMapper seasonHeaderSoapMapper

	void setup() {
		seasonHeaderSoapMapper = Mappers.getMapper(SeasonHeaderSoapMapper)
	}

	void "maps DB entity to SOAP header"() {
		given:
		Season season = new Season(
				uid: UID,
				title: TITLE)

		when:
		SeasonHeader seasonHeader = seasonHeaderSoapMapper.map(Lists.newArrayList(season))[0]

		then:
		seasonHeader.uid == UID
		seasonHeader.title == TITLE
	}

}
