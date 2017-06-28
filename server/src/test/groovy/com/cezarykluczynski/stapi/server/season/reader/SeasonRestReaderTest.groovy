package com.cezarykluczynski.stapi.server.season.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.SeasonBase
import com.cezarykluczynski.stapi.client.v1.rest.model.SeasonBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.SeasonFull
import com.cezarykluczynski.stapi.client.v1.rest.model.SeasonFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSort
import com.cezarykluczynski.stapi.model.season.entity.Season
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.season.dto.SeasonRestBeanParams
import com.cezarykluczynski.stapi.server.season.mapper.SeasonBaseRestMapper
import com.cezarykluczynski.stapi.server.season.mapper.SeasonFullRestMapper
import com.cezarykluczynski.stapi.server.season.query.SeasonRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class SeasonRestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private SeasonRestQuery seasonRestQueryBuilderMock

	private SeasonBaseRestMapper seasonBaseRestMapperMock

	private SeasonFullRestMapper seasonFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private SeasonRestReader seasonRestReader

	void setup() {
		seasonRestQueryBuilderMock = Mock()
		seasonBaseRestMapperMock = Mock()
		seasonFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		seasonRestReader = new SeasonRestReader(seasonRestQueryBuilderMock, seasonBaseRestMapperMock, seasonFullRestMapperMock, pageMapperMock,
				sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		SeasonBase seasonBase = Mock()
		Season season = Mock()
		SeasonRestBeanParams seasonRestBeanParams = Mock()
		List<SeasonBase> restSeasonList = Lists.newArrayList(seasonBase)
		List<Season> seasonList = Lists.newArrayList(season)
		Page<Season> seasonPage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		SeasonBaseResponse seasonResponseOutput = seasonRestReader.readBase(seasonRestBeanParams)

		then:
		1 * seasonRestQueryBuilderMock.query(seasonRestBeanParams) >> seasonPage
		1 * pageMapperMock.fromPageToRestResponsePage(seasonPage) >> responsePage
		1 * seasonRestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * seasonPage.content >> seasonList
		1 * seasonBaseRestMapperMock.mapBase(seasonList) >> restSeasonList
		0 * _
		seasonResponseOutput.seasons == restSeasonList
		seasonResponseOutput.page == responsePage
		seasonResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		SeasonFull seasonFull = Mock()
		Season season = Mock()
		List<Season> seasonList = Lists.newArrayList(season)
		Page<Season> seasonPage = Mock()

		when:
		SeasonFullResponse seasonResponseOutput = seasonRestReader.readFull(UID)

		then:
		1 * seasonRestQueryBuilderMock.query(_ as SeasonRestBeanParams) >> { SeasonRestBeanParams seasonRestBeanParams ->
			assert seasonRestBeanParams.uid == UID
			seasonPage
		}
		1 * seasonPage.content >> seasonList
		1 * seasonFullRestMapperMock.mapFull(season) >> seasonFull
		0 * _
		seasonResponseOutput.season == seasonFull
	}

	void "requires UID in full request"() {
		when:
		seasonRestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
