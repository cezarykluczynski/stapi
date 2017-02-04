package com.cezarykluczynski.stapi.server.comicSeries.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicSeries as RESTComicSeries
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicSeriesResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.model.comicSeries.entity.ComicSeries
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.comicSeries.dto.ComicSeriesRestBeanParams
import com.cezarykluczynski.stapi.server.comicSeries.mapper.ComicSeriesRestMapper
import com.cezarykluczynski.stapi.server.comicSeries.query.ComicSeriesRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class ComicSeriesRestReaderTest extends Specification {

	private static final String GUID = 'GUID'

	private ComicSeriesRestQuery comicSeriesRestQueryBuilderMock

	private ComicSeriesRestMapper comicSeriesRestMapperMock

	private PageMapper pageMapperMock

	private ComicSeriesRestReader comicSeriesRestReader

	void setup() {
		comicSeriesRestQueryBuilderMock = Mock(ComicSeriesRestQuery)
		comicSeriesRestMapperMock = Mock(ComicSeriesRestMapper)
		pageMapperMock = Mock(PageMapper)
		comicSeriesRestReader = new ComicSeriesRestReader(comicSeriesRestQueryBuilderMock, comicSeriesRestMapperMock, pageMapperMock)
	}

	void "gets database entities and puts them into ComicSeriesResponse"() {
		given:
		List<ComicSeries> dbComicSeriesList = Lists.newArrayList()
		Page<ComicSeries> dbComicSeriesPage = Mock(Page)
		dbComicSeriesPage.content >> dbComicSeriesList
		List<RESTComicSeries> soapComicSeriesList = Lists.newArrayList(new RESTComicSeries(guid: GUID))
		ComicSeriesRestBeanParams seriesRestBeanParams = Mock(ComicSeriesRestBeanParams)
		ResponsePage responsePage = Mock(ResponsePage)

		when:
		ComicSeriesResponse comicSeriesResponse = comicSeriesRestReader.read(seriesRestBeanParams)

		then:
		1 * comicSeriesRestQueryBuilderMock.query(seriesRestBeanParams) >> dbComicSeriesPage
		1 * pageMapperMock.fromPageToRestResponsePage(dbComicSeriesPage) >> responsePage
		1 * comicSeriesRestMapperMock.map(dbComicSeriesList) >> soapComicSeriesList
		comicSeriesResponse.comicSeries[0].guid == GUID
		comicSeriesResponse.page == responsePage
	}

}
