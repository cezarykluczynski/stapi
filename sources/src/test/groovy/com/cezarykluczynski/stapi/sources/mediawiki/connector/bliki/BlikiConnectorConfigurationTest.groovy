package com.cezarykluczynski.stapi.sources.mediawiki.connector.bliki

import com.cezarykluczynski.stapi.sources.mediawiki.configuration.MediaWikiSourcesProperties
import info.bliki.api.Connector
import info.bliki.api.User
import spock.lang.Specification

class BlikiConnectorConfigurationTest extends Specification {

	private static final String URL = 'URL'

	private MediaWikiSourcesProperties mediaWikiSourcesProperties

	private BlikiConnectorConfiguration blikiConnectorConfiguration

	def setup() {
		mediaWikiSourcesProperties = new MediaWikiSourcesProperties()
		mediaWikiSourcesProperties.setMemoryAlpha(URL)
		blikiConnectorConfiguration = new BlikiConnectorConfiguration()
		blikiConnectorConfiguration.mediaWikiSourcesProperties = mediaWikiSourcesProperties
	}

	def "creates user bean"() {
		when:
		User user = blikiConnectorConfiguration.user()

		then:
		user.actionUrl == URL
	}

	def "creates connector bean"() {
		when:
		Connector connector = blikiConnectorConfiguration.connector()

		then:
		connector instanceof Connector
	}

}
