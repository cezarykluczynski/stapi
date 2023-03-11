package com.cezarykluczynski.stapi.server.title.reader

import com.cezarykluczynski.stapi.client.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.rest.model.ResponseSort
import com.cezarykluczynski.stapi.client.rest.model.TitleV2Base
import com.cezarykluczynski.stapi.client.rest.model.TitleV2BaseResponse
import com.cezarykluczynski.stapi.client.rest.model.TitleV2Full
import com.cezarykluczynski.stapi.client.rest.model.TitleV2FullResponse
import com.cezarykluczynski.stapi.model.title.entity.Title
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.title.dto.TitleV2RestBeanParams
import com.cezarykluczynski.stapi.server.title.mapper.TitleBaseRestMapper
import com.cezarykluczynski.stapi.server.title.mapper.TitleFullRestMapper
import com.cezarykluczynski.stapi.server.title.query.TitleRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class TitleV2RestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private TitleRestQuery titleRestQueryBuilderMock

	private TitleBaseRestMapper titleBaseRestMapperMock

	private TitleFullRestMapper titleFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private TitleV2RestReader titleV2RestReader

	void setup() {
		titleRestQueryBuilderMock = Mock()
		titleBaseRestMapperMock = Mock()
		titleFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		titleV2RestReader = new TitleV2RestReader(titleRestQueryBuilderMock, titleBaseRestMapperMock, titleFullRestMapperMock, pageMapperMock,
				sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		TitleV2Base titleV2Base = Mock()
		Title title = Mock()
		TitleV2RestBeanParams titleV2RestBeanParams = Mock()
		List<TitleV2Base> restTitleList = Lists.newArrayList(titleV2Base)
		List<Title> titleList = Lists.newArrayList(title)
		Page<Title> titlePage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		TitleV2BaseResponse titleResponseOutput = titleV2RestReader.readBase(titleV2RestBeanParams)

		then:
		1 * titleRestQueryBuilderMock.query(titleV2RestBeanParams) >> titlePage
		1 * pageMapperMock.fromPageToRestResponsePage(titlePage) >> responsePage
		1 * titleV2RestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * titlePage.content >> titleList
		1 * titleBaseRestMapperMock.mapV2Base(titleList) >> restTitleList
		0 * _
		titleResponseOutput.titles == restTitleList
		titleResponseOutput.page == responsePage
		titleResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		TitleV2Full titleV2Full = Mock()
		Title title = Mock()
		List<Title> titleList = Lists.newArrayList(title)
		Page<Title> titlePage = Mock()

		when:
		TitleV2FullResponse titleResponseOutput = titleV2RestReader.readFull(UID)

		then:
		1 * titleRestQueryBuilderMock.query(_ as TitleV2RestBeanParams) >> { TitleV2RestBeanParams titleV2RestBeanParams ->
			assert titleV2RestBeanParams.uid == UID
			titlePage
		}
		1 * titlePage.content >> titleList
		1 * titleFullRestMapperMock.mapV2Full(title) >> titleV2Full
		0 * _
		titleResponseOutput.title == titleV2Full
	}

	void "requires UID in full request"() {
		when:
		titleV2RestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
