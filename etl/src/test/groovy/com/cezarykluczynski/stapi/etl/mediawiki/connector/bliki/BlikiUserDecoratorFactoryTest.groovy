package com.cezarykluczynski.stapi.etl.mediawiki.connector.bliki

import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.etl.mediawiki.configuration.MediaWikiSourceProperties
import com.cezarykluczynski.stapi.etl.mediawiki.configuration.MediaWikiSourcesProperties
import spock.lang.Specification

class BlikiUserDecoratorFactoryTest extends Specification {

	static final String MEMORY_ALPHA_API_URL = 'MEMORY_ALPHA_API_URL'
	static final String MEMORY_BETA_API_URL = 'MEMORY_BETA_API_URL'
	static final String TECHNICAL_HELPER_API_URL = 'TECHNICAL_HELPER_API_URL'

	private BlikiUserDecoratorFactory blikiUserDecoratorFactory

	void "map is set, when connectors are created"() {
		given:
		MediaWikiSourcesProperties mediaWikiSourcesProperties = new MediaWikiSourcesProperties()
		mediaWikiSourcesProperties.setMemoryAlphaEn(new MediaWikiSourceProperties(apiUrl: MEMORY_ALPHA_API_URL))
		mediaWikiSourcesProperties.setMemoryBetaEn(new MediaWikiSourceProperties(apiUrl: MEMORY_BETA_API_URL))
		mediaWikiSourcesProperties.setTechnicalHelper(new MediaWikiSourceProperties(apiUrl: TECHNICAL_HELPER_API_URL))

		when:
		blikiUserDecoratorFactory = new BlikiUserDecoratorFactory(mediaWikiSourcesProperties)

		then:
		blikiUserDecoratorFactory.createFor(MediaWikiSource.MEMORY_ALPHA_EN).actionUrl == MEMORY_ALPHA_API_URL
		blikiUserDecoratorFactory.createFor(MediaWikiSource.MEMORY_BETA_EN).actionUrl == MEMORY_BETA_API_URL
		blikiUserDecoratorFactory.createFor(MediaWikiSource.TECHNICAL_HELPER).actionUrl == TECHNICAL_HELPER_API_URL
	}

}
