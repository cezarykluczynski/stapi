package com.cezarykluczynski.stapi.server.series.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSort
import com.cezarykluczynski.stapi.client.v1.rest.model.SeriesBase
import com.cezarykluczynski.stapi.client.v1.rest.model.SeriesBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.SeriesFull
import com.cezarykluczynski.stapi.client.v1.rest.model.SeriesFullResponse
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.series.dto.SeriesRestBeanParams
import com.cezarykluczynski.stapi.server.series.mapper.SeriesBaseRestMapper
import com.cezarykluczynski.stapi.server.series.mapper.SeriesFullRestMapper
import com.cezarykluczynski.stapi.server.series.query.SeriesRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class SeriesRestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private SeriesRestQuery seriesRestQueryBuilderMock

	private SeriesBaseRestMapper seriesBaseRestMapperMock

	private SeriesFullRestMapper seriesFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private SeriesRestReader seriesRestReader

	void setup() {
		seriesRestQueryBuilderMock = Mock()
		seriesBaseRestMapperMock = Mock()
		seriesFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		seriesRestReader = new SeriesRestReader(seriesRestQueryBuilderMock, seriesBaseRestMapperMock, seriesFullRestMapperMock, pageMapperMock,
				sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		SeriesBase seriesBase = Mock()
		Series series = Mock()
		SeriesRestBeanParams seriesRestBeanParams = Mock()
		List<SeriesBase> restSeriesList = Lists.newArrayList(seriesBase)
		List<Series> seriesList = Lists.newArrayList(series)
		Page<Series> seriesPage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		SeriesBaseResponse seriesResponseOutput = seriesRestReader.readBase(seriesRestBeanParams)

		then:
		1 * seriesRestQueryBuilderMock.query(seriesRestBeanParams) >> seriesPage
		1 * pageMapperMock.fromPageToRestResponsePage(seriesPage) >> responsePage
		1 * seriesRestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * seriesPage.content >> seriesList
		1 * seriesBaseRestMapperMock.mapBase(seriesList) >> restSeriesList
		0 * _
		seriesResponseOutput.series == restSeriesList
		seriesResponseOutput.page == responsePage
		seriesResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		SeriesFull seriesFull = Mock()
		Series series = Mock()
		List<Series> seriesList = Lists.newArrayList(series)
		Page<Series> seriesPage = Mock()

		when:
		SeriesFullResponse seriesResponseOutput = seriesRestReader.readFull(UID)

		then:
		1 * seriesRestQueryBuilderMock.query(_ as SeriesRestBeanParams) >> { SeriesRestBeanParams seriesRestBeanParams ->
			assert seriesRestBeanParams.uid == UID
			seriesPage
		}
		1 * seriesPage.content >> seriesList
		1 * seriesFullRestMapperMock.mapFull(series) >> seriesFull
		0 * _
		seriesResponseOutput.series == seriesFull
	}

	void "requires UID in full request"() {
		when:
		seriesRestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
