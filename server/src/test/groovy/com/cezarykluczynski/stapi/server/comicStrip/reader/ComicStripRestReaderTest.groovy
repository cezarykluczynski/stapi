package com.cezarykluczynski.stapi.server.comicStrip.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicStripBase
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicStripBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicStripFull
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicStripFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.model.comicStrip.entity.ComicStrip
import com.cezarykluczynski.stapi.server.comicStrip.dto.ComicStripRestBeanParams
import com.cezarykluczynski.stapi.server.comicStrip.mapper.ComicStripBaseRestMapper
import com.cezarykluczynski.stapi.server.comicStrip.mapper.ComicStripFullRestMapper
import com.cezarykluczynski.stapi.server.comicStrip.query.ComicStripRestQuery
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class ComicStripRestReaderTest extends Specification {

	private static final String UID = 'UID'

	private ComicStripRestQuery comicStripRestQueryBuilderMock

	private ComicStripBaseRestMapper comicStripBaseRestMapperMock

	private ComicStripFullRestMapper comicStripFullRestMapperMock

	private PageMapper pageMapperMock

	private ComicStripRestReader comicStripRestReader

	void setup() {
		comicStripRestQueryBuilderMock = Mock()
		comicStripBaseRestMapperMock = Mock()
		comicStripFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		comicStripRestReader = new ComicStripRestReader(comicStripRestQueryBuilderMock, comicStripBaseRestMapperMock, comicStripFullRestMapperMock,
				pageMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		ComicStripBase comicStripBase = Mock()
		ComicStrip comicStrip = Mock()
		ComicStripRestBeanParams comicStripRestBeanParams = Mock()
		List<ComicStripBase> restComicStripList = Lists.newArrayList(comicStripBase)
		List<ComicStrip> comicStripList = Lists.newArrayList(comicStrip)
		Page<ComicStrip> comicStripPage = Mock()
		ResponsePage responsePage = Mock()

		when:
		ComicStripBaseResponse comicStripResponseOutput = comicStripRestReader.readBase(comicStripRestBeanParams)

		then:
		1 * comicStripRestQueryBuilderMock.query(comicStripRestBeanParams) >> comicStripPage
		1 * pageMapperMock.fromPageToRestResponsePage(comicStripPage) >> responsePage
		1 * comicStripPage.content >> comicStripList
		1 * comicStripBaseRestMapperMock.mapBase(comicStripList) >> restComicStripList
		0 * _
		comicStripResponseOutput.comicStrips == restComicStripList
		comicStripResponseOutput.page == responsePage
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		ComicStripFull comicStripFull = Mock()
		ComicStrip comicStrip = Mock()
		List<ComicStrip> comicStripList = Lists.newArrayList(comicStrip)
		Page<ComicStrip> comicStripPage = Mock()

		when:
		ComicStripFullResponse comicStripResponseOutput = comicStripRestReader.readFull(UID)

		then:
		1 * comicStripRestQueryBuilderMock.query(_ as ComicStripRestBeanParams) >> { ComicStripRestBeanParams comicStripRestBeanParams ->
			assert comicStripRestBeanParams.uid == UID
			comicStripPage
		}
		1 * comicStripPage.content >> comicStripList
		1 * comicStripFullRestMapperMock.mapFull(comicStrip) >> comicStripFull
		0 * _
		comicStripResponseOutput.comicStrip == comicStripFull
	}

	void "requires UID in full request"() {
		when:
		comicStripRestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
