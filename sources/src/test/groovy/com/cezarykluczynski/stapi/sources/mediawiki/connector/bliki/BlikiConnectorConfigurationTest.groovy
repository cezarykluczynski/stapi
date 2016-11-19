package com.cezarykluczynski.stapi.sources.mediawiki.connector.bliki

import com.cezarykluczynski.stapi.sources.mediawiki.configuration.MediaWikiSourcesProperties
import spock.lang.Specification

class BlikiConnectorConfigurationTest extends Specification {

	private static final String MEMORY_ALPHA_EN_API_URL = 'MEMORY_ALPHA_EN_API_URL'
	private static final String MEMORY_BETA_EN_API_URL = 'MEMORY_BETA_EN_API_URL'

	private MediaWikiSourcesProperties mediaWikiSourcesProperties

	private BlikiConnectorConfiguration blikiConnectorConfiguration

	def setup() {
		mediaWikiSourcesProperties = new MediaWikiSourcesProperties()
		mediaWikiSourcesProperties.memoryAlphaEnApiUrl = MEMORY_ALPHA_EN_API_URL
		mediaWikiSourcesProperties.memoryBetaEnApiUrl = MEMORY_BETA_EN_API_URL
		blikiConnectorConfiguration = new BlikiConnectorConfiguration()
		blikiConnectorConfiguration.mediaWikiSourcesProperties = mediaWikiSourcesProperties
	}

	def "creates Memory Alpha EN user decorator"() {
		when:
		UserDecorator userDecorator = blikiConnectorConfiguration.memoryAlphaEnUserDecorator()

		then:
		userDecorator.actionUrl == MEMORY_ALPHA_EN_API_URL
	}

	def "creates Memory Beta EN user decorator"() {
		when:
		UserDecorator userDecorator = blikiConnectorConfiguration.memoryBetaEnUserDecorator()

		then:
		userDecorator.actionUrl == MEMORY_BETA_EN_API_URL
	}

}
