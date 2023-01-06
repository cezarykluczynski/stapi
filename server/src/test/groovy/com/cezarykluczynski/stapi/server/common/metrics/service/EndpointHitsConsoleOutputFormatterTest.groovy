package com.cezarykluczynski.stapi.server.common.metrics.service

import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType
import com.cezarykluczynski.stapi.model.endpoint_hit.dto.MetricsEndpointKeyDTO
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointDetailDTO
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointDetailsDTO
import com.cezarykluczynski.stapi.server.common.reader.CommonEntitiesDetailsReader
import com.google.common.collect.Maps
import spock.lang.Specification

import java.time.LocalDateTime

class EndpointHitsConsoleOutputFormatterTest extends Specification {

	private static final String METHOD_NAME = 'METHOD_NAME'
	private static final String DATE = LocalDateTime.now().format(EndpointHitsConsoleOutputFormatter.DATE_TIME_FORMATTER)

	private CommonEntitiesDetailsReader commonEntitiesDetailsReaderMock

	private EndpointHitsConsoleOutputFormatter endpointHitsPersister

	void setup() {
		commonEntitiesDetailsReaderMock = Mock()
		endpointHitsPersister = new EndpointHitsConsoleOutputFormatter(commonEntitiesDetailsReaderMock)
	}

	void "formats entities for console print"() {
		given:
		RestEndpointDetailDTO title = new RestEndpointDetailDTO(name: 'Title', type: TrackedEntityType.FICTIONAL_PRIMARY)
		RestEndpointDetailDTO weapon = new RestEndpointDetailDTO(name: 'Weapon', type: TrackedEntityType.FICTIONAL_PRIMARY)
		RestEndpointDetailDTO animal = new RestEndpointDetailDTO(name: 'Animal', type: TrackedEntityType.FICTIONAL_PRIMARY)
		RestEndpointDetailDTO comics = new RestEndpointDetailDTO(name: 'Comics', type: TrackedEntityType.REAL_WORLD_PRIMARY)
		RestEndpointDetailsDTO restEndpointDetailsDTO = new RestEndpointDetailsDTO([title, weapon, animal, comics])
		Map<MetricsEndpointKeyDTO, Long> endpointsHits = Maps.newHashMap()
		endpointsHits.put(MetricsEndpointKeyDTO.of('CommonRestEndpoint', METHOD_NAME), 5L) // invalid, should be skipped
		endpointsHits.put(MetricsEndpointKeyDTO.of('TitleRestEndpoint', METHOD_NAME), 7L)
		endpointsHits.put(MetricsEndpointKeyDTO.of('TitleV2RestEndpoint', METHOD_NAME), 11L)
		endpointsHits.put(MetricsEndpointKeyDTO.of('TitleSoapEndpoint', METHOD_NAME), 13L)
		endpointsHits.put(MetricsEndpointKeyDTO.of('TitleV2SoapEndpoint', METHOD_NAME), 15L) // invalid, should be skipped
		endpointsHits.put(MetricsEndpointKeyDTO.of('WeaponRestEndpoint', METHOD_NAME), 17L)
		endpointsHits.put(MetricsEndpointKeyDTO.of('WeaponV2RestEndpoint', METHOD_NAME), 19L)
		endpointsHits.put(MetricsEndpointKeyDTO.of('WeaponSoapEndpoint', METHOD_NAME), 23L)
		endpointsHits.put(MetricsEndpointKeyDTO.of('AnimalRestEndpoint', METHOD_NAME), 29L)
		endpointsHits.put(MetricsEndpointKeyDTO.of('ComicsSoapEndpoint', METHOD_NAME), 31L)

		when:
		String consoleOutput = endpointHitsPersister.formatForConsolePrint(endpointsHits)

		then:
		1 * commonEntitiesDetailsReaderMock.details() >> restEndpointDetailsDTO
		0 * _
		consoleOutput == """
Reports hits since application startup (0 days ago on ${DATE}):
Entity | REST hits | SOAP hits | Total hits | REST hits % | SOAP hits %
Total  |        83 |        67 |        150 |       55.0% |       45.0%
------------------------------------------------------------------------
Weapon |        36 |        23 |         59 |       61.0% |       39.0%
Title  |        18 |        13 |         31 |       58.0% |       42.0%
Comics |         0 |        31 |         31 |        0.0% |      100.0%
Animal |        29 |         0 |         29 |      100.0% |        0.0%"""
	}

	void "formats entities for console print, when there are no stats"() {
		given:
		RestEndpointDetailDTO title = new RestEndpointDetailDTO(name: 'Title', type: TrackedEntityType.FICTIONAL_PRIMARY)
		RestEndpointDetailDTO weapon = new RestEndpointDetailDTO(name: 'Weapon', type: TrackedEntityType.FICTIONAL_PRIMARY)
		RestEndpointDetailDTO animal = new RestEndpointDetailDTO(name: 'Animal', type: TrackedEntityType.FICTIONAL_PRIMARY)
		RestEndpointDetailDTO comics = new RestEndpointDetailDTO(name: 'Comics', type: TrackedEntityType.REAL_WORLD_PRIMARY)
		RestEndpointDetailsDTO restEndpointDetailsDTO = new RestEndpointDetailsDTO([title, weapon, animal, comics])
		Map<MetricsEndpointKeyDTO, Long> endpointsHits = Maps.newHashMap()

		when:
		String consoleOutput = endpointHitsPersister.formatForConsolePrint(endpointsHits)

		then:
		1 * commonEntitiesDetailsReaderMock.details() >> restEndpointDetailsDTO
		0 * _
		consoleOutput == """
Reports hits since application startup (0 days ago on ${DATE}):
Entity | REST hits | SOAP hits | Total hits | REST hits % | SOAP hits %
Total  |         0 |         0 |          0 |           - |           -"""
	}

}
