package com.cezarykluczynski.stapi.server.comicStrip.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicStrip as RESTComicStrip
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicStripResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.model.comicStrip.entity.ComicStrip
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.comicStrip.dto.ComicStripRestBeanParams
import com.cezarykluczynski.stapi.server.comicStrip.mapper.ComicStripRestMapper
import com.cezarykluczynski.stapi.server.comicStrip.query.ComicStripRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class ComicStripRestReaderTest extends Specification {

	private static final String GUID = 'GUID'

	private ComicStripRestQuery comicStripRestQueryBuilderMock

	private ComicStripRestMapper comicStripRestMapperMock

	private PageMapper pageMapperMock

	private ComicStripRestReader comicStripRestReader

	void setup() {
		comicStripRestQueryBuilderMock = Mock(ComicStripRestQuery)
		comicStripRestMapperMock = Mock(ComicStripRestMapper)
		pageMapperMock = Mock(PageMapper)
		comicStripRestReader = new ComicStripRestReader(comicStripRestQueryBuilderMock, comicStripRestMapperMock, pageMapperMock)
	}

	void "gets database entities and puts them into ComicStripResponse"() {
		given:
		List<ComicStrip> dbComicStripList = Lists.newArrayList()
		Page<ComicStrip> dbComicStripPage = Mock(Page)
		dbComicStripPage.content >> dbComicStripList
		List<RESTComicStrip> soapComicStripList = Lists.newArrayList(new RESTComicStrip(guid: GUID))
		ComicStripRestBeanParams seriesRestBeanParams = Mock(ComicStripRestBeanParams)
		ResponsePage responsePage = Mock(ResponsePage)

		when:
		ComicStripResponse comicStripResponse = comicStripRestReader.readBase(seriesRestBeanParams)

		then:
		1 * comicStripRestQueryBuilderMock.query(seriesRestBeanParams) >> dbComicStripPage
		1 * pageMapperMock.fromPageToRestResponsePage(dbComicStripPage) >> responsePage
		1 * comicStripRestMapperMock.map(dbComicStripList) >> soapComicStripList
		comicStripResponse.comicStrips[0].guid == GUID
		comicStripResponse.page == responsePage
	}

}
