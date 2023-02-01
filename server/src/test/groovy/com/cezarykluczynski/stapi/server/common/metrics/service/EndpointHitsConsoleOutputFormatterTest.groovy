package com.cezarykluczynski.stapi.server.common.metrics.service

import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType
import com.cezarykluczynski.stapi.model.endpoint_hit.dto.MetricsEndpointKeyDTO
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointDetailDTO
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointDetailsDTO
import com.cezarykluczynski.stapi.server.common.reader.CommonEntitiesDetailsReader
import com.google.common.collect.Maps
import spock.lang.Specification

import java.time.LocalDateTime
import java.util.regex.Pattern

class EndpointHitsConsoleOutputFormatterTest extends Specification {

	private static final Pattern PATTERN_LINE_ENDINGS = Pattern.compile('\\r\\n|\\n|\\r')
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
		RestEndpointDetailsDTO restEndpointDetailsDTO = new RestEndpointDetailsDTO([title, weapon, animal])
		Map<MetricsEndpointKeyDTO, Long> endpointsHits = Maps.newHashMap()
		endpointsHits.put(MetricsEndpointKeyDTO.of('CommonRestEndpoint', METHOD_NAME), 5L) // invalid, should be skipped
		endpointsHits.put(MetricsEndpointKeyDTO.of('TitleRestEndpoint', METHOD_NAME), 7L)
		endpointsHits.put(MetricsEndpointKeyDTO.of('TitleV2RestEndpoint', METHOD_NAME), 11L)
		endpointsHits.put(MetricsEndpointKeyDTO.of('TitleV2GrpcEndpoint', METHOD_NAME), 15L) // invalid, should be skipped
		endpointsHits.put(MetricsEndpointKeyDTO.of('WeaponRestEndpoint', METHOD_NAME), 17L)
		endpointsHits.put(MetricsEndpointKeyDTO.of('WeaponV2RestEndpoint', METHOD_NAME), 19L)
		endpointsHits.put(MetricsEndpointKeyDTO.of('AnimalRestEndpoint', METHOD_NAME), 29L)

		when:
		String consoleOutput = endpointHitsPersister.formatForConsolePrint(endpointsHits)

		then:
		1 * commonEntitiesDetailsReaderMock.details() >> restEndpointDetailsDTO
		0 * _
		normalizeLineEndings(consoleOutput) == normalizeLineEndings("""
Reports hits since application startup (0 days ago on ${DATE}):
Entity | REST hits | Total hits | REST hits %
Total  |        83 |         83 |        100%
---------------------------------------------
Weapon |        36 |         36 |        100%
Animal |        29 |         29 |        100%
Title  |        18 |         18 |        100%""")
	}

	void "formats entities for console print, when there are no stats"() {
		given:
		RestEndpointDetailDTO title = new RestEndpointDetailDTO(name: 'Title', type: TrackedEntityType.FICTIONAL_PRIMARY)
		RestEndpointDetailDTO weapon = new RestEndpointDetailDTO(name: 'Weapon', type: TrackedEntityType.FICTIONAL_PRIMARY)
		RestEndpointDetailDTO animal = new RestEndpointDetailDTO(name: 'Animal', type: TrackedEntityType.FICTIONAL_PRIMARY)
		RestEndpointDetailsDTO restEndpointDetailsDTO = new RestEndpointDetailsDTO([title, weapon, animal])
		Map<MetricsEndpointKeyDTO, Long> endpointsHits = Maps.newHashMap()

		when:
		String consoleOutput = endpointHitsPersister.formatForConsolePrint(endpointsHits)

		then:
		1 * commonEntitiesDetailsReaderMock.details() >> restEndpointDetailsDTO
		0 * _
		normalizeLineEndings(consoleOutput) == normalizeLineEndings("""
Reports hits since application startup (0 days ago on ${DATE}):
Entity | REST hits | Total hits | REST hits %
Total  |         0 |          0 |           -""")
	}

	private static String normalizeLineEndings(String string) {
		PATTERN_LINE_ENDINGS.matcher(string).replaceAll('\n')
	}

}
