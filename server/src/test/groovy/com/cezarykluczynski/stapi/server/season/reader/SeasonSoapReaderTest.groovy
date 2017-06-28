package com.cezarykluczynski.stapi.server.season.reader

import com.cezarykluczynski.stapi.client.v1.soap.SeasonBase
import com.cezarykluczynski.stapi.client.v1.soap.SeasonBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.SeasonBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.SeasonFull
import com.cezarykluczynski.stapi.client.v1.soap.SeasonFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.SeasonFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.RequestSort
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.client.v1.soap.ResponseSort
import com.cezarykluczynski.stapi.model.season.entity.Season
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.season.mapper.SeasonBaseSoapMapper
import com.cezarykluczynski.stapi.server.season.mapper.SeasonFullSoapMapper
import com.cezarykluczynski.stapi.server.season.query.SeasonSoapQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class SeasonSoapReaderTest extends Specification {

	private static final String UID = 'UID'

	private SeasonSoapQuery seasonSoapQueryBuilderMock

	private SeasonBaseSoapMapper seasonBaseSoapMapperMock

	private SeasonFullSoapMapper seasonFullSoapMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private SeasonSoapReader seasonSoapReader

	void setup() {
		seasonSoapQueryBuilderMock = Mock()
		seasonBaseSoapMapperMock = Mock()
		seasonFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		seasonSoapReader = new SeasonSoapReader(seasonSoapQueryBuilderMock, seasonBaseSoapMapperMock, seasonFullSoapMapperMock, pageMapperMock,
				sortMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<Season> seasonList = Lists.newArrayList()
		Page<Season> seasonPage = Mock()
		List<SeasonBase> soapSeasonList = Lists.newArrayList(new SeasonBase(uid: UID))
		SeasonBaseRequest seasonBaseRequest = Mock()
		ResponsePage responsePage = Mock()
		RequestSort requestSort = Mock()
		ResponseSort responseSort = Mock()

		when:
		SeasonBaseResponse seasonResponse = seasonSoapReader.readBase(seasonBaseRequest)

		then:
		1 * seasonSoapQueryBuilderMock.query(seasonBaseRequest) >> seasonPage
		1 * seasonPage.content >> seasonList
		1 * pageMapperMock.fromPageToSoapResponsePage(seasonPage) >> responsePage
		1 * seasonBaseRequest.sort >> requestSort
		1 * sortMapperMock.map(requestSort) >> responseSort
		1 * seasonBaseSoapMapperMock.mapBase(seasonList) >> soapSeasonList
		0 * _
		seasonResponse.seasons[0].uid == UID
		seasonResponse.page == responsePage
		seasonResponse.sort == responseSort
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		SeasonFull seasonFull = new SeasonFull(uid: UID)
		Season season = Mock()
		Page<Season> seasonPage = Mock()
		SeasonFullRequest seasonFullRequest = new SeasonFullRequest(uid: UID)

		when:
		SeasonFullResponse seasonFullResponse = seasonSoapReader.readFull(seasonFullRequest)

		then:
		1 * seasonSoapQueryBuilderMock.query(seasonFullRequest) >> seasonPage
		1 * seasonPage.content >> Lists.newArrayList(season)
		1 * seasonFullSoapMapperMock.mapFull(season) >> seasonFull
		0 * _
		seasonFullResponse.season.uid == UID
	}

	void "requires UID in full request"() {
		given:
		SeasonFullRequest seasonFullRequest = Mock()

		when:
		seasonSoapReader.readFull(seasonFullRequest)

		then:
		thrown(MissingUIDException)
	}

}
