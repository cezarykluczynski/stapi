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

	def setup() {
		mediaWikiSourcesPropertiesMock = Mock(MediaWikiSourcesProperties)
		mediaWikiMiminalIntervalConfigurationStrategy = Mock(MediaWikiMinimalIntervalConfigurationStrategy)
	}

	def "uses dependencies to do configuration"() {
		when:
		mediaWikiMinimalIntervalProvider = new MediaWikiMinimalIntervalProvider(mediaWikiSourcesPropertiesMock,
				mediaWikiMiminalIntervalConfigurationStrategy)

		then:
		1 * mediaWikiSourcesPropertiesMock.getMemoryAlphaEnApiUrl() >> MEMORY_ALPHA_EN_URL
		1 * mediaWikiSourcesPropertiesMock.getMemoryAlphaEnMinimalInterval() >> MEMORY_ALPHA_EN_MINIMAL_INTERVAL
		1 * mediaWikiMiminalIntervalConfigurationStrategy.configureInterval(MEMORY_ALPHA_EN_URL, MEMORY_ALPHA_EN_MINIMAL_INTERVAL) >> MEMORY_ALPHA_EN_INTERVAL
		mediaWikiMinimalIntervalProvider.memoryAlphaEnInterval == MEMORY_ALPHA_EN_INTERVAL
		1 * mediaWikiSourcesPropertiesMock.getMemoryBetaEnApiUrl() >> MEMORY_BETA_EN_URL
		1 * mediaWikiSourcesPropertiesMock.getMemoryBetaEnMinimalInterval() >> MEMORY_BETA_EN_MINIMAL_INTERVAL
		1 * mediaWikiMiminalIntervalConfigurationStrategy.configureInterval(MEMORY_BETA_EN_URL, MEMORY_BETA_EN_MINIMAL_INTERVAL) >> MEMORY_BETA_EN_INTERVAL
		mediaWikiMinimalIntervalProvider.memoryBetaEnInterval == MEMORY_BETA_EN_INTERVAL
	}

}
