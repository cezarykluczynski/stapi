package com.cezarykluczynski.stapi.server.common.endpoint

import com.cezarykluczynski.stapi.server.common.dto.DataVersionDTO
import com.cezarykluczynski.stapi.server.common.dto.PongDTO
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointDetailsDTO
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointStatisticsDTO
import com.cezarykluczynski.stapi.server.common.healthcheck.CommonDatabaseStatusValidator
import com.cezarykluczynski.stapi.server.common.reader.CommonDataReader
import com.cezarykluczynski.stapi.server.common.feature_switch.api.FeatureSwitchApi
import com.cezarykluczynski.stapi.server.common.feature_switch.dto.FeatureSwitchesDTO
import jakarta.ws.rs.core.Response
import spock.lang.Specification

class CommonRestEndpointTest extends Specification {

	private CommonDataReader commonDataReaderMock

	private FeatureSwitchApi featureSwitchApiMock

	private CommonDatabaseStatusValidator commonDatabaseStatusValidatorMock

	private CommonRestEndpoint commonRestEndpoint

	void setup() {
		commonDataReaderMock = Mock()
		featureSwitchApiMock = Mock()
		commonDatabaseStatusValidatorMock = Mock()
		commonRestEndpoint = new CommonRestEndpoint(commonDataReaderMock, featureSwitchApiMock, commonDatabaseStatusValidatorMock)
	}

	void "gets feature switches"() {
		given:
		FeatureSwitchesDTO featureSwitchesDTO = FeatureSwitchesDTO.of([])

		when:
		FeatureSwitchesDTO featureSwitchesDTOOutput = commonRestEndpoint.featureSwitches()

		then:
		1 * featureSwitchApiMock.all >> featureSwitchesDTO
		0 * _
		featureSwitchesDTOOutput == featureSwitchesDTO
	}

	void "gets entities statistics from CommonDataReader"() {
		given:
		RestEndpointStatisticsDTO restEndpointStatisticsDTO = Mock()

		when:
		RestEndpointStatisticsDTO restEndpointStatisticsDTOOutput = commonRestEndpoint.entitiesStatistics()

		then:
		1 * commonDataReaderMock.entitiesStatistics() >> restEndpointStatisticsDTO
		0 * _
		restEndpointStatisticsDTOOutput == restEndpointStatisticsDTO
	}

	void "gets details from CommonDataReader"() {
		given:
		RestEndpointDetailsDTO restEndpointDetailsDTO = Mock()

		when:
		RestEndpointDetailsDTO restEndpointDetailsDTOOutput = commonRestEndpoint.details()

		then:
		1 * commonDataReaderMock.details() >> restEndpointDetailsDTO
		0 * _
		restEndpointDetailsDTOOutput == restEndpointDetailsDTO
	}

	void "responds to ping"() {
		when:
		PongDTO pongDTO = commonRestEndpoint.ping()

		then:
		1 * commonDatabaseStatusValidatorMock.validateDatabaseAccess()
		0 * _
		pongDTO.pong == 'pong'
	}

	void "gets zipped TOS form"() {
		given:
		Response response = Mock()

		when:
		Response responseOutput = commonRestEndpoint.tosFormZip()

		then:
		1 * commonDataReaderMock.tosFormZip() >> response
		0 * _
		responseOutput == response
	}

	void "gets stapi.yaml"() {
		given:
		Response response = Mock()

		when:
		Response responseOutput = commonRestEndpoint.stapiYaml()

		then:
		1 * commonDataReaderMock.stapiYaml() >> response
		0 * _
		responseOutput == response
	}

	void "gets data version"() {
		given:
		DataVersionDTO dataVersionDTO = Mock()

		when:
		DataVersionDTO dataVersionDTOOutput = commonRestEndpoint.dataVersion()

		then:
		1 * commonDataReaderMock.dataVersion() >> dataVersionDTO
		0 * _
		dataVersionDTOOutput == dataVersionDTO
	}

}
