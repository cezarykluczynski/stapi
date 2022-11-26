package com.cezarykluczynski.stapi.server.panel.endpoint

import com.cezarykluczynski.stapi.server.github.model.GitHubDTO
import com.cezarykluczynski.stapi.server.github.service.GitHubApi
import com.cezarykluczynski.stapi.util.feature_switch.api.FeatureSwitchApi
import com.cezarykluczynski.stapi.util.feature_switch.dto.FeatureSwitchesDTO
import spock.lang.Specification

class PanelCommonEndpointTest extends Specification {

	private FeatureSwitchApi featureSwitchApiMock

	private  GitHubApi gitHubApiMock

	private PanelCommonEndpoint panelCommonEndpoint

	void setup() {
		featureSwitchApiMock = Mock()
		gitHubApiMock = Mock()
		panelCommonEndpoint = new PanelCommonEndpoint(featureSwitchApiMock, gitHubApiMock)
	}

	void "gets feature switches"() {
		given:
		FeatureSwitchesDTO featureSwitchesDTO = Mock()

		when:
		FeatureSwitchesDTO featureSwitchesDTOOutput = panelCommonEndpoint.featureSwitches()

		then:
		1 * featureSwitchApiMock.all >> featureSwitchesDTO
		0 * _
		featureSwitchesDTOOutput == featureSwitchesDTO
	}

	void "gets GitHub project details"() {
		given:
		GitHubDTO gitHubDTO = Mock()

		when:
		GitHubDTO gitHubDTOOutput = panelCommonEndpoint.gitHubProjectDetails()

		then:
		1 * gitHubApiMock.projectDetails >> gitHubDTO
		0 * _
		gitHubDTOOutput == gitHubDTO
	}

}
