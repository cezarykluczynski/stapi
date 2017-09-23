package com.cezarykluczynski.stapi.server.title.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.TitleBase
import com.cezarykluczynski.stapi.client.v1.rest.model.TitleBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.TitleFull
import com.cezarykluczynski.stapi.client.v1.rest.model.TitleFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSort
import com.cezarykluczynski.stapi.model.title.entity.Title
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.title.dto.TitleRestBeanParams
import com.cezarykluczynski.stapi.server.title.mapper.TitleBaseRestMapper
import com.cezarykluczynski.stapi.server.title.mapper.TitleFullRestMapper
import com.cezarykluczynski.stapi.server.title.query.TitleRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class TitleRestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private TitleRestQuery titleRestQueryBuilderMock

	private TitleBaseRestMapper titleBaseRestMapperMock

	private TitleFullRestMapper titleFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private TitleRestReader titleRestReader

	void setup() {
		titleRestQueryBuilderMock = Mock()
		titleBaseRestMapperMock = Mock()
		titleFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		titleRestReader = new TitleRestReader(titleRestQueryBuilderMock, titleBaseRestMapperMock, titleFullRestMapperMock, pageMapperMock,
				sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		TitleBase titleBase = Mock()
		Title title = Mock()
		TitleRestBeanParams titleRestBeanParams = Mock()
		List<TitleBase> restTitleList = Lists.newArrayList(titleBase)
		List<Title> titleList = Lists.newArrayList(title)
		Page<Title> titlePage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		TitleBaseResponse titleResponseOutput = titleRestReader.readBase(titleRestBeanParams)

		then:
		1 * titleRestQueryBuilderMock.query(titleRestBeanParams) >> titlePage
		1 * pageMapperMock.fromPageToRestResponsePage(titlePage) >> responsePage
		1 * titleRestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * titlePage.content >> titleList
		1 * titleBaseRestMapperMock.mapBase(titleList) >> restTitleList
		0 * _
		titleResponseOutput.titles == restTitleList
		titleResponseOutput.page == responsePage
		titleResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		TitleFull titleFull = Mock()
		Title title = Mock()
		List<Title> titleList = Lists.newArrayList(title)
		Page<Title> titlePage = Mock()

		when:
		TitleFullResponse titleResponseOutput = titleRestReader.readFull(UID)

		then:
		1 * titleRestQueryBuilderMock.query(_ as TitleRestBeanParams) >> { TitleRestBeanParams titleRestBeanParams ->
			assert titleRestBeanParams.uid == UID
			titlePage
		}
		1 * titlePage.content >> titleList
		1 * titleFullRestMapperMock.mapFull(title) >> titleFull
		0 * _
		titleResponseOutput.title == titleFull
	}

	void "requires UID in full request"() {
		when:
		titleRestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
