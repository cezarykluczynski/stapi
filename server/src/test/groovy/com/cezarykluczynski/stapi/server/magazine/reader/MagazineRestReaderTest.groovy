package com.cezarykluczynski.stapi.server.magazine.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineBase
import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineFull
import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSort
import com.cezarykluczynski.stapi.model.magazine.entity.Magazine
import com.cezarykluczynski.stapi.server.magazine.dto.MagazineRestBeanParams
import com.cezarykluczynski.stapi.server.magazine.mapper.MagazineBaseRestMapper
import com.cezarykluczynski.stapi.server.magazine.mapper.MagazineFullRestMapper
import com.cezarykluczynski.stapi.server.magazine.query.MagazineRestQuery
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class MagazineRestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private MagazineRestQuery magazineRestQueryBuilderMock

	private MagazineBaseRestMapper magazineBaseRestMapperMock

	private MagazineFullRestMapper magazineFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private MagazineRestReader magazineRestReader

	void setup() {
		magazineRestQueryBuilderMock = Mock()
		magazineBaseRestMapperMock = Mock()
		magazineFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		magazineRestReader = new MagazineRestReader(magazineRestQueryBuilderMock, magazineBaseRestMapperMock, magazineFullRestMapperMock, pageMapperMock,
				sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		MagazineBase magazineBase = Mock()
		Magazine magazine = Mock()
		MagazineRestBeanParams magazineRestBeanParams = Mock()
		List<MagazineBase> restMagazineList = Lists.newArrayList(magazineBase)
		List<Magazine> magazineList = Lists.newArrayList(magazine)
		Page<Magazine> magazinePage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		MagazineBaseResponse magazineResponseOutput = magazineRestReader.readBase(magazineRestBeanParams)

		then:
		1 * magazineRestQueryBuilderMock.query(magazineRestBeanParams) >> magazinePage
		1 * pageMapperMock.fromPageToRestResponsePage(magazinePage) >> responsePage
		1 * magazineRestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * magazinePage.content >> magazineList
		1 * magazineBaseRestMapperMock.mapBase(magazineList) >> restMagazineList
		0 * _
		magazineResponseOutput.magazines == restMagazineList
		magazineResponseOutput.page == responsePage
		magazineResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		MagazineFull magazineFull = Mock()
		Magazine magazine = Mock()
		List<Magazine> magazineList = Lists.newArrayList(magazine)
		Page<Magazine> magazinePage = Mock()

		when:
		MagazineFullResponse magazineResponseOutput = magazineRestReader.readFull(UID)

		then:
		1 * magazineRestQueryBuilderMock.query(_ as MagazineRestBeanParams) >> { MagazineRestBeanParams magazineRestBeanParams ->
			assert magazineRestBeanParams.uid == UID
			magazinePage
		}
		1 * magazinePage.content >> magazineList
		1 * magazineFullRestMapperMock.mapFull(magazine) >> magazineFull
		0 * _
		magazineResponseOutput.magazine == magazineFull
	}

	void "requires UID in full request"() {
		when:
		magazineRestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
