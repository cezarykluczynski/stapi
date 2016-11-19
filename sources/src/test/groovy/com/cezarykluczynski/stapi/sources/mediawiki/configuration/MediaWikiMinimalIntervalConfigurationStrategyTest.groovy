package com.cezarykluczynski.stapi.sources.mediawiki.configuration

import org.springframework.beans.factory.BeanInitializationException
import spock.lang.Specification

class MediaWikiMinimalIntervalConfigurationStrategyTest extends Specification {

	private static final String WIKIA_URL = 'memory-alpha.wikia.com'
	private static final String NON_WIKIA_URL = 'NON_WIKIA_URL'
	private static final String VALID_INTERVAL_STRING = '1500'
	private static final String VALID_INTERVAL_AUTO = 'auto'
	private static final String INVALID_INTERVAL_STRING = 'ABC'
	private static final Long VALID_INTERVAL_LONG = 1500L
	private static final Long ZERO_INTERVAL = 0L

	private MediaWikiMinimalIntervalConfigurationStrategy mediaWikiMinimalIntervalConfigurationStrategy

	def setup() {
		mediaWikiMinimalIntervalConfigurationStrategy = new MediaWikiMinimalIntervalConfigurationStrategy()
	}

	def "throws exception when URL is null"() {
		when:
		mediaWikiMinimalIntervalConfigurationStrategy.configureInterval(null, VALID_INTERVAL_STRING)

		then:
		thrown(NullPointerException)
	}

	def "when minimal interval is null, and URL is non-Wikia, zero is returned"() {
		when:
		Long interval = mediaWikiMinimalIntervalConfigurationStrategy.configureInterval(NON_WIKIA_URL, null)

		then:
		interval == ZERO_INTERVAL
	}

	def "when minimal interval is auto, and URL is non-Wikia, zero is returned"() {
		when:
		Long interval = mediaWikiMinimalIntervalConfigurationStrategy.configureInterval(NON_WIKIA_URL, VALID_INTERVAL_AUTO)

		then:
		interval == ZERO_INTERVAL
	}


	def "when minimal interval is null, and URL is Wikia, default non-zero interval is returned"() {
		when:
		Long interval = mediaWikiMinimalIntervalConfigurationStrategy.configureInterval(WIKIA_URL, null)

		then:
		interval == MediaWikiMinimalIntervalConfigurationStrategy.WIKIA_INTERVAL
	}

	def "when minimal interval is not null, and URL is Wikia, default non-zero interval is returned"() {
		when:
		Long interval = mediaWikiMinimalIntervalConfigurationStrategy.configureInterval(WIKIA_URL, VALID_INTERVAL_AUTO)

		then:
		interval == MediaWikiMinimalIntervalConfigurationStrategy.WIKIA_INTERVAL
	}

	def "when minimal interval is not null, it is returned"() {
		when:
		Long interval = mediaWikiMinimalIntervalConfigurationStrategy.configureInterval(NON_WIKIA_URL, VALID_INTERVAL_STRING)

		then:
		interval == VALID_INTERVAL_LONG
	}

	def "when minimal interval cannot be parsed, exception is thrown"() {
		when:
		mediaWikiMinimalIntervalConfigurationStrategy.configureInterval(NON_WIKIA_URL, INVALID_INTERVAL_STRING)

		then:
		thrown(BeanInitializationException)
	}

}
