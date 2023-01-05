package com.cezarykluczynski.stapi.server.common.documentation.service

import com.cezarykluczynski.stapi.server.common.feature_switch.api.FeatureSwitchApi
import com.cezarykluczynski.stapi.server.common.feature_switch.dto.FeatureSwitchType
import jakarta.ws.rs.core.Response
import org.springframework.core.io.ClassPathResource
import spock.lang.Specification

class TosAttachmentProviderTest extends Specification {

	private static final String NAME = 'NAME'

	private FeatureSwitchApi featureSwitchApiMock

	private DocumentationProvider documentationProviderMock

	private TosAttachmentProvider tosAttachmentProvider

	void setup() {
		featureSwitchApiMock = Mock()
		documentationProviderMock = Mock()
		tosAttachmentProvider = new TosAttachmentProvider(featureSwitchApiMock, documentationProviderMock)
	}

	void "provides response for when TOS and PP feature switch is off"() {
		when:
		Response response = tosAttachmentProvider.provide(NAME)

		then:
		1 * featureSwitchApiMock.isEnabled(FeatureSwitchType.TOS_AND_PP) >> false
		0 * _
		response.status == Response.Status.NOT_FOUND.statusCode
	}

	void "provides response for when TOS and PP feature switch is on and Docker feature switch is on"() {
		given:
		Response response = Mock()

		when:
		Response responseOutput = tosAttachmentProvider.provide(NAME)

		then:
		1 * featureSwitchApiMock.isEnabled(FeatureSwitchType.TOS_AND_PP) >> true
		1 * featureSwitchApiMock.isEnabled(FeatureSwitchType.DOCKER) >> true
		1 * documentationProviderMock.provideFile("/$NAME", NAME) >> response
		0 * _
		responseOutput == response
	}

	void "provides response for when TOS and PP feature switch is on and Docker feature switch is off"() {
		given:
		Response response = Mock()

		when:
		Response responseOutput = tosAttachmentProvider.provide(NAME)

		then:
		1 * featureSwitchApiMock.isEnabled(FeatureSwitchType.TOS_AND_PP) >> true
		1 * featureSwitchApiMock.isEnabled(FeatureSwitchType.DOCKER) >> false
		1 * documentationProviderMock.provideFile(_ as ClassPathResource, NAME) >> response
		0 * _
		responseOutput == response
	}

}
