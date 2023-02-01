package com.cezarykluczynski.stapi.server.common.endpoint

import com.cezarykluczynski.stapi.server.common.documentation.dto.DocumentationDTO
import com.cezarykluczynski.stapi.server.common.dto.DataVersionDTO
import com.cezarykluczynski.stapi.server.common.dto.PongDTO
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointDetailsDTO
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointStatisticsDTO
import com.cezarykluczynski.stapi.server.common.healthcheck.CommonDatabaseStatusValidator
import com.cezarykluczynski.stapi.server.common.reader.CommonDataReader
import com.cezarykluczynski.stapi.server.github.model.GitHubDTO
import com.cezarykluczynski.stapi.server.github.service.GitHubApi
import com.cezarykluczynski.stapi.server.common.feature_switch.api.FeatureSwitchApi
import com.cezarykluczynski.stapi.server.common.feature_switch.dto.FeatureSwitchesDTO
import jakarta.ws.rs.core.Response
import spock.lang.Specification

class CommonRestEndpointTest extends Specification {

	private CommonDataReader commonDataReaderMock

	private FeatureSwitchApi featureSwitchApiMock

	private GitHubApi gitHubApiMock

	private CommonDatabaseStatusValidator commonDatabaseStatusValidatorMock

	private CommonRestEndpoint commonRestEndpoint

	void setup() {
		commonDataReaderMock = Mock()
		featureSwitchApiMock = Mock()
		gitHubApiMock = Mock()
		commonDatabaseStatusValidatorMock = Mock()
		commonRestEndpoint = new CommonRestEndpoint(commonDataReaderMock, featureSwitchApiMock, gitHubApiMock, commonDatabaseStatusValidatorMock)
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

	void "gets GitHub project details switches"() {
		given:
		GitHubDTO gitHubDTO = Mock()

		when:
		GitHubDTO gitHubDTOOutput = commonRestEndpoint.gitHubProjectDetails()

		then:
		1 * gitHubApiMock.projectDetails >> gitHubDTO
		0 * _
		gitHubDTOOutput == gitHubDTO
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

	void "gets documentation from CommonDataReader"() {
		given:
		DocumentationDTO documentationDTO = Mock()

		when:
		DocumentationDTO documentationDTOOutput = commonRestEndpoint.documentation()

		then:
		1 * commonDataReaderMock.documentation() >> documentationDTO
		0 * _
		documentationDTOOutput == documentationDTO
	}

	void "responds to ping"() {
		when:
		PongDTO pongDTO = commonRestEndpoint.ping()

		then:
		1 * commonDatabaseStatusValidatorMock.validateDatabaseAccess()
		0 * _
		pongDTO.pong == 'pong'
	}

	void "gets zipped REST documentation"() {
		given:
		Response response = Mock()

		when:
		Response responseOutput = commonRestEndpoint.restSpecsZip()

		then:
		1 * commonDataReaderMock.restSpecsZip() >> response
		0 * _
		responseOutput == response
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
