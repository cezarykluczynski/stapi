package com.cezarykluczynski.stapi.sources.mediawiki.connector.bliki

import info.bliki.api.Connector
import info.bliki.api.User
import spock.lang.Specification

class BlikiConnectorConfigurationTest extends Specification {

	private static final String URL = 'URL'

	private BlikiConnectorProperties blikiConnectorProperties

	private BlikiConnectorConfiguration blikiConnectorConfiguration

	def setup() {
		blikiConnectorProperties = new BlikiConnectorProperties()
		blikiConnectorProperties.setSourceUrl(URL)
		blikiConnectorConfiguration = new BlikiConnectorConfiguration()
		blikiConnectorConfiguration.blikiConnectorProperties = blikiConnectorProperties
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
