package com.cezarykluczynski.stapi.server.magazine_series.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineSeriesBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineSeriesFullResponse
import com.cezarykluczynski.stapi.server.magazine_series.dto.MagazineSeriesRestBeanParams
import com.cezarykluczynski.stapi.server.magazine_series.reader.MagazineSeriesRestReader
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest

class MagazineSeriesRestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String TITLE = 'TITLE'

	private MagazineSeriesRestReader magazineSeriesRestReaderMock

	private MagazineSeriesRestEndpoint magazineSeriesRestEndpoint

	void setup() {
		magazineSeriesRestReaderMock = Mock()
		magazineSeriesRestEndpoint = new MagazineSeriesRestEndpoint(magazineSeriesRestReaderMock)
	}

	void "passes get call to MagazineSeriesRestReader"() {
		given:
		MagazineSeriesFullResponse magazineSeriesFullResponse = Mock()

		when:
		MagazineSeriesFullResponse magazineSeriesFullResponseOutput = magazineSeriesRestEndpoint.getMagazineSeries(UID)

		then:
		1 * magazineSeriesRestReaderMock.readFull(UID) >> magazineSeriesFullResponse
		magazineSeriesFullResponseOutput == magazineSeriesFullResponse
	}

	void "passes search get call to MagazineSeriesRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		MagazineSeriesBaseResponse magazineSeriesBaseResponse = Mock()

		when:
		MagazineSeriesBaseResponse magazineSeriesBaseResponseOutput = magazineSeriesRestEndpoint.searchMagazineSeries(pageAwareBeanParams)

		then:
		1 * magazineSeriesRestReaderMock.readBase(_ as MagazineSeriesRestBeanParams) >> { MagazineSeriesRestBeanParams magazineSeriesRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			magazineSeriesBaseResponse
		}
		magazineSeriesBaseResponseOutput == magazineSeriesBaseResponse
	}

	void "passes search post call to MagazineSeriesRestReader"() {
		given:
		MagazineSeriesRestBeanParams magazineSeriesRestBeanParams = new MagazineSeriesRestBeanParams(title: TITLE)
		MagazineSeriesBaseResponse magazineSeriesBaseResponse = Mock()

		when:
		MagazineSeriesBaseResponse magazineSeriesBaseResponseOutput = magazineSeriesRestEndpoint.searchMagazineSeries(magazineSeriesRestBeanParams)

		then:
		1 * magazineSeriesRestReaderMock.readBase(magazineSeriesRestBeanParams as MagazineSeriesRestBeanParams) >> {
				MagazineSeriesRestBeanParams magazineSeriesRestBeanParamsInput ->
			assert magazineSeriesRestBeanParamsInput.title == TITLE
			magazineSeriesBaseResponse
		}
		magazineSeriesBaseResponseOutput == magazineSeriesBaseResponse
	}

}
