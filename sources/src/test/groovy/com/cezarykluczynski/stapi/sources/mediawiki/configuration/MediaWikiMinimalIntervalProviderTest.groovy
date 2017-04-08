package com.cezarykluczynski.stapi.sources.mediawiki.configuration

import spock.lang.Specification

class MediaWikiMinimalIntervalProviderTest extends Specification {

	private static final String MEMORY_ALPHA_EN_URL = 'MEMORY_ALPHA_EN_URL'
	private static final String MEMORY_ALPHA_EN_MINIMAL_INTERVAL = 'MEMORY_ALPHA_EN_MINIMAL_INTERVAL'
	private static final Long MEMORY_ALPHA_EN_INTERVAL = 300L
	private static final String MEMORY_BETA_EN_URL = 'MEMORY_BETA_EN_URL'
	private static final String MEMORY_BETA_EN_MINIMAL_INTERVAL = 'MEMORY_BETA_EN_MINIMAL_INTERVAL'
	private static final Long MEMORY_BETA_EN_INTERVAL = 400L

	private MediaWikiSourcesProperties mediaWikiSourcesPropertiesMock

	private MediaWikiMinimalIntervalConfigurationStrategy mediaWikiMiminalIntervalConfigurationStrategy

	private MediaWikiMinimalIntervalProvider mediaWikiMinimalIntervalProvider

	void setup() {
		mediaWikiSourcesPropertiesMock = Mock()
		mediaWikiMiminalIntervalConfigurationStrategy = Mock()
	}

	void "uses dependencies to do configuration"() {
		given:
		MediaWikiSourceProperties memoryAlphaEn = Mock()
		MediaWikiSourceProperties memoryBetaEn = Mock()

		when:
		mediaWikiMinimalIntervalProvider = new MediaWikiMinimalIntervalProvider(mediaWikiSourcesPropertiesMock,
				mediaWikiMiminalIntervalConfigurationStrategy)

		then:
		2 * mediaWikiSourcesPropertiesMock.memoryAlphaEn >> memoryAlphaEn
		1 * memoryAlphaEn.apiUrl >> MEMORY_ALPHA_EN_URL
		1 * memoryAlphaEn.minimalInterval >> MEMORY_ALPHA_EN_MINIMAL_INTERVAL
		1 * mediaWikiMiminalIntervalConfigurationStrategy.configureInterval(MEMORY_ALPHA_EN_URL, MEMORY_ALPHA_EN_MINIMAL_INTERVAL) >>
				MEMORY_ALPHA_EN_INTERVAL
		mediaWikiMinimalIntervalProvider.memoryAlphaEnInterval == MEMORY_ALPHA_EN_INTERVAL
		2 * mediaWikiSourcesPropertiesMock.memoryBetaEn >> memoryBetaEn
		1 * memoryBetaEn.apiUrl >> MEMORY_BETA_EN_URL
		1 * memoryBetaEn.minimalInterval >> MEMORY_BETA_EN_MINIMAL_INTERVAL
		1 * mediaWikiMiminalIntervalConfigurationStrategy.configureInterval(MEMORY_BETA_EN_URL, MEMORY_BETA_EN_MINIMAL_INTERVAL) >>
				MEMORY_BETA_EN_INTERVAL
		mediaWikiMinimalIntervalProvider.memoryBetaEnInterval == MEMORY_BETA_EN_INTERVAL
	}

}
