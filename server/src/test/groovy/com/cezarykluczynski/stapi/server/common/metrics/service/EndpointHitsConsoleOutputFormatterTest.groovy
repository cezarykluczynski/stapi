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
	private static final String GET_METHOD_NAME = 'getMethodName'
	private static final String SEARCH_METHOD_NAME = 'searchMethodName'
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
		endpointsHits.put(MetricsEndpointKeyDTO.of('CommonRestEndpoint', GET_METHOD_NAME, true), 5L) // invalid, should be skipped
		endpointsHits.put(MetricsEndpointKeyDTO.of('TitleRestEndpoint', GET_METHOD_NAME, false), 7L)
		endpointsHits.put(MetricsEndpointKeyDTO.of('TitleRestEndpoint', GET_METHOD_NAME, true), 8L)
		endpointsHits.put(MetricsEndpointKeyDTO.of('TitleV2RestEndpoint', GET_METHOD_NAME, false), 11L)
		endpointsHits.put(MetricsEndpointKeyDTO.of('TitleV2RestEndpoint', GET_METHOD_NAME, true), 12L)
		endpointsHits.put(MetricsEndpointKeyDTO.of('TitleV2RestEndpoint', SEARCH_METHOD_NAME, false), 13L)
		endpointsHits.put(MetricsEndpointKeyDTO.of('TitleV2RestEndpoint', SEARCH_METHOD_NAME, true), 14L)
		endpointsHits.put(MetricsEndpointKeyDTO.of('TitleV2GrpcEndpoint', GET_METHOD_NAME, true), 15L) // invalid, should be skipped
		endpointsHits.put(MetricsEndpointKeyDTO.of('WeaponRestEndpoint', GET_METHOD_NAME, true), 17L)
		endpointsHits.put(MetricsEndpointKeyDTO.of('WeaponRestEndpoint', GET_METHOD_NAME, true), 18L)
		endpointsHits.put(MetricsEndpointKeyDTO.of('WeaponV2RestEndpoint', GET_METHOD_NAME, true), 20L)
		endpointsHits.put(MetricsEndpointKeyDTO.of('WeaponV3RestEndpoint', GET_METHOD_NAME, true), 22L)
		endpointsHits.put(MetricsEndpointKeyDTO.of('WeaponV2RestEndpoint', SEARCH_METHOD_NAME, true), 24L)
		endpointsHits.put(MetricsEndpointKeyDTO.of('WeaponV3RestEndpoint', SEARCH_METHOD_NAME, true), 26L)
		endpointsHits.put(MetricsEndpointKeyDTO.of('AnimalRestEndpoint', GET_METHOD_NAME, true), 29L)

		when:
		String consoleOutput = endpointHitsPersister.formatForConsolePrint(endpointsHits)

		then:
		1 * commonEntitiesDetailsReaderMock.details() >> restEndpointDetailsDTO
		0 * _
		normalizeLineEndings(consoleOutput) == normalizeLineEndings("""
Reports hits since application startup (0 days ago on ${DATE}):
Frontend:                                             || Outside the frontend:
                | Hits V1 | Hits V2 | Hits V3 | Total || Hits V1 | Hits V2 | Hits V3 | Total
          Total |      55 |      70 |      48 |   173 ||       7 |      24 |       0 |    31
         Search |       0 |      38 |      26 |    64 ||       0 |      13 |       0 |    13
            Get |      55 |      32 |      22 |   109 ||       7 |      11 |       0 |    18
------------------------------------------------------++------------------------------------
(SEARCH) Weapon |       0 |      24 |      26 |    50 ||       0 |       0 |       0 |     0
          Title |       0 |      14 |       - |    14 ||       0 |      13 |       - |    13
------------------------------------------------------++------------------------------------
   (GET) Weapon |      18 |      20 |      22 |    60 ||       0 |       0 |       0 |     0
          Title |       8 |      12 |       - |    20 ||       7 |      11 |       - |    18
         Animal |      29 |       - |       - |    29 ||       0 |       - |       - |     0""")
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
Frontend:                || Outside the frontend:
       | Hits V1 | Total || Hits V1 | Total
 Total |       0 |     0 ||       0 |     0
Search |       0 |     0 ||       0 |     0
   Get |       0 |     0 ||       0 |     0""")
	}

	private static String normalizeLineEndings(String string) {
		PATTERN_LINE_ENDINGS.matcher(string).replaceAll('\n')
	}

}
