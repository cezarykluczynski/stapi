package com.cezarykluczynski.stapi.server.comic_series.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicSeriesBase
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicSeriesBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicSeriesFull
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicSeriesFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSort
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.comic_series.dto.ComicSeriesRestBeanParams
import com.cezarykluczynski.stapi.server.comic_series.mapper.ComicSeriesBaseRestMapper
import com.cezarykluczynski.stapi.server.comic_series.mapper.ComicSeriesFullRestMapper
import com.cezarykluczynski.stapi.server.comic_series.query.ComicSeriesRestQuery
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class ComicSeriesRestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private ComicSeriesRestQuery comicSeriesRestQueryBuilderMock

	private ComicSeriesBaseRestMapper comicSeriesBaseRestMapperMock

	private ComicSeriesFullRestMapper comicSeriesFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private ComicSeriesRestReader comicSeriesRestReader

	void setup() {
		comicSeriesRestQueryBuilderMock = Mock()
		comicSeriesBaseRestMapperMock = Mock()
		comicSeriesFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		comicSeriesRestReader = new ComicSeriesRestReader(comicSeriesRestQueryBuilderMock, comicSeriesBaseRestMapperMock,
				comicSeriesFullRestMapperMock, pageMapperMock, sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		ComicSeriesBase comicSeriesBase = Mock()
		ComicSeries comicSeries = Mock()
		ComicSeriesRestBeanParams comicSeriesRestBeanParams = Mock()
		List<ComicSeriesBase> restComicSeriesList = Lists.newArrayList(comicSeriesBase)
		List<ComicSeries> comicSeriesList = Lists.newArrayList(comicSeries)
		Page<ComicSeries> comicSeriesPage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		ComicSeriesBaseResponse comicSeriesResponseOutput = comicSeriesRestReader.readBase(comicSeriesRestBeanParams)

		then:
		1 * comicSeriesRestQueryBuilderMock.query(comicSeriesRestBeanParams) >> comicSeriesPage
		1 * pageMapperMock.fromPageToRestResponsePage(comicSeriesPage) >> responsePage
		1 * comicSeriesRestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * comicSeriesPage.content >> comicSeriesList
		1 * comicSeriesBaseRestMapperMock.mapBase(comicSeriesList) >> restComicSeriesList
		0 * _
		comicSeriesResponseOutput.comicSeries == restComicSeriesList
		comicSeriesResponseOutput.page == responsePage
		comicSeriesResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		ComicSeriesFull comicSeriesFull = Mock()
		ComicSeries comicSeries = Mock()
		List<ComicSeries> comicSeriesList = Lists.newArrayList(comicSeries)
		Page<ComicSeries> comicSeriesPage = Mock()

		when:
		ComicSeriesFullResponse comicSeriesResponseOutput = comicSeriesRestReader.readFull(UID)

		then:
		1 * comicSeriesRestQueryBuilderMock.query(_ as ComicSeriesRestBeanParams) >> { ComicSeriesRestBeanParams comicSeriesRestBeanParams ->
			assert comicSeriesRestBeanParams.uid == UID
			comicSeriesPage
		}
		1 * comicSeriesPage.content >> comicSeriesList
		1 * comicSeriesFullRestMapperMock.mapFull(comicSeries) >> comicSeriesFull
		0 * _
		comicSeriesResponseOutput.comicSeries == comicSeriesFull
	}

	void "requires UID in full request"() {
		when:
		comicSeriesRestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
