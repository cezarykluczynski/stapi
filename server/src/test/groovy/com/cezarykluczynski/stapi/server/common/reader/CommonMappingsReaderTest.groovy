package com.cezarykluczynski.stapi.server.common.reader

import com.cezarykluczynski.stapi.model.common.service.EntityMatadataProvider
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointMappingsDTO
import com.google.common.collect.Maps
import spock.lang.Specification

class CommonMappingsReaderTest extends Specification {

	private EntityMatadataProvider entityMatadataProviderMock

	private CommonMappingsReader commonMappingsReader

	void setup() {
		entityMatadataProviderMock = Mock()
		commonMappingsReader = new CommonMappingsReader(entityMatadataProviderMock)
	}

	void "builds mappings"() {
		given:
		Map<String, String> classNameToSymbolMap = Maps.newHashMap()
		classNameToSymbolMap.put('com.cezarykluczynski.stapi.model.throttle.entity.Throttle', 'TH')
		classNameToSymbolMap.put('com.cezarykluczynski.stapi.model.series.entity.Series', 'SE')

		when:
		RestEndpointMappingsDTO restEndpointMappingsDTO = commonMappingsReader.mappings()

		then:
		1 * entityMatadataProviderMock.provideClassNameToSymbolMap() >> classNameToSymbolMap
		0 * _
		restEndpointMappingsDTO.prefix == '/api/v1/'
		restEndpointMappingsDTO.urls.size() == 1
		restEndpointMappingsDTO.urls[0].name == 'Series'
		restEndpointMappingsDTO.urls[0].suffix == 'series'
		restEndpointMappingsDTO.urls[0].symbol == 'SE'
	}

}
