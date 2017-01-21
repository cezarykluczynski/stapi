package com.cezarykluczynski.stapi.sources.mediawiki.connector.bliki

import com.cezarykluczynski.stapi.sources.mediawiki.configuration.MediaWikiSourceProperties
import com.cezarykluczynski.stapi.sources.mediawiki.configuration.MediaWikiSourcesProperties
import spock.lang.Specification

class BlikiConnectorConfigurationTest extends Specification {

	private static final String MEMORY_ALPHA_EN_API_URL = 'MEMORY_ALPHA_EN_API_URL'
	private static final String MEMORY_BETA_EN_API_URL = 'MEMORY_BETA_EN_API_URL'
	private static final String TECHNICAL_HELPER_API_URL = 'TECHNICAL_HELPER_API_URL'

	private MediaWikiSourcesProperties mediaWikiSourcesProperties

	private BlikiConnectorConfiguration blikiConnectorConfiguration

	void setup() {
		mediaWikiSourcesProperties = new MediaWikiSourcesProperties()

		mediaWikiSourcesProperties.memoryAlphaEn = new MediaWikiSourceProperties()
		mediaWikiSourcesProperties.memoryAlphaEn.apiUrl = MEMORY_ALPHA_EN_API_URL
		mediaWikiSourcesProperties.memoryBetaEn = new MediaWikiSourceProperties()
		mediaWikiSourcesProperties.memoryBetaEn.apiUrl = MEMORY_BETA_EN_API_URL
		mediaWikiSourcesProperties.technicalHelper = new MediaWikiSourceProperties()
		mediaWikiSourcesProperties.technicalHelper.apiUrl = TECHNICAL_HELPER_API_URL
		blikiConnectorConfiguration = new BlikiConnectorConfiguration(
				mediaWikiSourcesProperties: mediaWikiSourcesProperties
		)
	}

	void "creates Memory Alpha EN user decorator"() {
		when:
		UserDecorator userDecorator = blikiConnectorConfiguration.memoryAlphaEnUserDecorator()

		then:
		userDecorator.actionUrl == MEMORY_ALPHA_EN_API_URL
	}

	void "creates Memory Beta EN user decorator"() {
		when:
		UserDecorator userDecorator = blikiConnectorConfiguration.memoryBetaEnUserDecorator()

		then:
		userDecorator.actionUrl == MEMORY_BETA_EN_API_URL
	}

	void "creates technical helper user decorator"() {
		when:
		UserDecorator userDecorator = blikiConnectorConfiguration.technicalHelperUserDecorator()

		then:
		userDecorator.actionUrl == TECHNICAL_HELPER_API_URL
	}

}
