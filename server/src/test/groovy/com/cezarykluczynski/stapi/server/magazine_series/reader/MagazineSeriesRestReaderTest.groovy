package com.cezarykluczynski.stapi.server.magazine_series.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineSeriesBase
import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineSeriesBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineSeriesFull
import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineSeriesFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSort
import com.cezarykluczynski.stapi.model.magazine_series.entity.MagazineSeries
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.magazine_series.dto.MagazineSeriesRestBeanParams
import com.cezarykluczynski.stapi.server.magazine_series.mapper.MagazineSeriesBaseRestMapper
import com.cezarykluczynski.stapi.server.magazine_series.mapper.MagazineSeriesFullRestMapper
import com.cezarykluczynski.stapi.server.magazine_series.query.MagazineSeriesRestQuery
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class MagazineSeriesRestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private MagazineSeriesRestQuery magazineSeriesRestQueryBuilderMock

	private MagazineSeriesBaseRestMapper magazineSeriesBaseRestMapperMock

	private MagazineSeriesFullRestMapper magazineSeriesFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private MagazineSeriesRestReader magazineSeriesRestReader

	void setup() {
		magazineSeriesRestQueryBuilderMock = Mock()
		magazineSeriesBaseRestMapperMock = Mock()
		magazineSeriesFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		magazineSeriesRestReader = new MagazineSeriesRestReader(magazineSeriesRestQueryBuilderMock, magazineSeriesBaseRestMapperMock,
				magazineSeriesFullRestMapperMock, pageMapperMock, sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		MagazineSeriesBase magazineSeriesBase = Mock()
		MagazineSeries magazineSeries = Mock()
		MagazineSeriesRestBeanParams magazineSeriesRestBeanParams = Mock()
		List<MagazineSeriesBase> restMagazineSeriesList = Lists.newArrayList(magazineSeriesBase)
		List<MagazineSeries> magazineSeriesList = Lists.newArrayList(magazineSeries)
		Page<MagazineSeries> magazineSeriesPage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		MagazineSeriesBaseResponse magazineSeriesResponseOutput = magazineSeriesRestReader.readBase(magazineSeriesRestBeanParams)

		then:
		1 * magazineSeriesRestQueryBuilderMock.query(magazineSeriesRestBeanParams) >> magazineSeriesPage
		1 * pageMapperMock.fromPageToRestResponsePage(magazineSeriesPage) >> responsePage
		1 * magazineSeriesRestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * magazineSeriesPage.content >> magazineSeriesList
		1 * magazineSeriesBaseRestMapperMock.mapBase(magazineSeriesList) >> restMagazineSeriesList
		0 * _
		magazineSeriesResponseOutput.magazineSeries == restMagazineSeriesList
		magazineSeriesResponseOutput.page == responsePage
		magazineSeriesResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		MagazineSeriesFull magazineSeriesFull = Mock()
		MagazineSeries magazineSeries = Mock()
		List<MagazineSeries> magazineSeriesList = Lists.newArrayList(magazineSeries)
		Page<MagazineSeries> magazineSeriesPage = Mock()

		when:
		MagazineSeriesFullResponse magazineSeriesResponseOutput = magazineSeriesRestReader.readFull(UID)

		then:
		1 * magazineSeriesRestQueryBuilderMock.query(_ as MagazineSeriesRestBeanParams) >> {
				MagazineSeriesRestBeanParams magazineSeriesRestBeanParams ->
			assert magazineSeriesRestBeanParams.uid == UID
			magazineSeriesPage
		}
		1 * magazineSeriesPage.content >> magazineSeriesList
		1 * magazineSeriesFullRestMapperMock.mapFull(magazineSeries) >> magazineSeriesFull
		0 * _
		magazineSeriesResponseOutput.magazineSeries == magazineSeriesFull
	}

	void "requires UID in full request"() {
		when:
		magazineSeriesRestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
