package com.cezarykluczynski.stapi.server.material.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.MaterialBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.MaterialFullResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.material.dto.MaterialRestBeanParams
import com.cezarykluczynski.stapi.server.material.reader.MaterialRestReader

class MaterialRestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String NAME = 'NAME'

	private MaterialRestReader materialRestReaderMock

	private MaterialRestEndpoint materialRestEndpoint

	void setup() {
		materialRestReaderMock = Mock()
		materialRestEndpoint = new MaterialRestEndpoint(materialRestReaderMock)
	}

	void "passes get call to MaterialRestReader"() {
		given:
		MaterialFullResponse materialFullResponse = Mock()

		when:
		MaterialFullResponse materialFullResponseOutput = materialRestEndpoint.getMaterial(UID)

		then:
		1 * materialRestReaderMock.readFull(UID) >> materialFullResponse
		materialFullResponseOutput == materialFullResponse
	}

	void "passes search get call to MaterialRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		MaterialBaseResponse materialResponse = Mock()

		when:
		MaterialBaseResponse materialResponseOutput = materialRestEndpoint.searchMaterials(pageAwareBeanParams)

		then:
		1 * materialRestReaderMock.readBase(_ as MaterialRestBeanParams) >> { MaterialRestBeanParams materialRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			materialResponse
		}
		materialResponseOutput == materialResponse
	}

	void "passes search post call to MaterialRestReader"() {
		given:
		MaterialRestBeanParams materialRestBeanParams = new MaterialRestBeanParams(name: NAME)
		MaterialBaseResponse materialResponse = Mock()

		when:
		MaterialBaseResponse materialResponseOutput = materialRestEndpoint.searchMaterials(materialRestBeanParams)

		then:
		1 * materialRestReaderMock.readBase(materialRestBeanParams as MaterialRestBeanParams) >> { MaterialRestBeanParams params ->
			assert params.name == NAME
			materialResponse
		}
		materialResponseOutput == materialResponse
	}

}
