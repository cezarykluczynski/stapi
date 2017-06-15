package com.cezarykluczynski.stapi.server.magazine.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineFullResponse
import com.cezarykluczynski.stapi.server.magazine.dto.MagazineRestBeanParams
import com.cezarykluczynski.stapi.server.magazine.reader.MagazineRestReader
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest

class MagazineRestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String TITLE = 'TITLE'

	private MagazineRestReader magazineRestReaderMock

	private MagazineRestEndpoint magazineRestEndpoint

	void setup() {
		magazineRestReaderMock = Mock()
		magazineRestEndpoint = new MagazineRestEndpoint(magazineRestReaderMock)
	}

	void "passes get call to MagazineRestReader"() {
		given:
		MagazineFullResponse magazineFullResponse = Mock()

		when:
		MagazineFullResponse magazineFullResponseOutput = magazineRestEndpoint.getMagazine(UID)

		then:
		1 * magazineRestReaderMock.readFull(UID) >> magazineFullResponse
		magazineFullResponseOutput == magazineFullResponse
	}

	void "passes search get call to MagazineRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		MagazineBaseResponse magazineResponse = Mock()

		when:
		MagazineBaseResponse magazineResponseOutput = magazineRestEndpoint.searchMagazine(pageAwareBeanParams)

		then:
		1 * magazineRestReaderMock.readBase(_ as MagazineRestBeanParams) >> { MagazineRestBeanParams magazineRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			magazineResponse
		}
		magazineResponseOutput == magazineResponse
	}

	void "passes search post call to MagazineRestReader"() {
		given:
		MagazineRestBeanParams magazineRestBeanParams = new MagazineRestBeanParams(title: TITLE)
		MagazineBaseResponse magazineResponse = Mock()

		when:
		MagazineBaseResponse magazineResponseOutput = magazineRestEndpoint.searchMagazine(magazineRestBeanParams)

		then:
		1 * magazineRestReaderMock.readBase(magazineRestBeanParams as MagazineRestBeanParams) >> { MagazineRestBeanParams params ->
			assert params.title == TITLE
			magazineResponse
		}
		magazineResponseOutput == magazineResponse
	}

}
