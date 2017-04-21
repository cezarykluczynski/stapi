package com.cezarykluczynski.stapi.sources.mediawiki.configuration

import com.cezarykluczynski.stapi.sources.mediawiki.service.wikia.WikiaUrlDetector
import org.springframework.beans.factory.BeanInitializationException
import spock.lang.Specification

class MediaWikiMinimalIntervalConfigurationStrategyTest extends Specification {

	private static final String API_URL = 'API_URL'
	private static final String VALID_INTERVAL_STRING = '1500'
	private static final String VALID_INTERVAL_AUTO = 'auto'
	private static final String INVALID_INTERVAL_STRING = 'ABC'
	private static final Long VALID_INTERVAL_LONG = 1500L
	private static final Long ZERO_INTERVAL = 0L

	private WikiaUrlDetector wikiaUrlDetectorMock

	private MediaWikiMinimalIntervalConfigurationStrategy mediaWikiMinimalIntervalConfigurationStrategy

	void setup() {
		wikiaUrlDetectorMock = Mock()
		mediaWikiMinimalIntervalConfigurationStrategy = new MediaWikiMinimalIntervalConfigurationStrategy(wikiaUrlDetectorMock)
	}

	void "throws exception when URL is null"() {
		when:
		mediaWikiMinimalIntervalConfigurationStrategy.configureInterval(null, VALID_INTERVAL_STRING)

		then:
		thrown(NullPointerException)
	}

	void "when minimal interval is null, and URL is non-Wikia, zero is returned"() {
		when:
		Long interval = mediaWikiMinimalIntervalConfigurationStrategy.configureInterval(API_URL, null)

		then:
		1 * wikiaUrlDetectorMock.isWikiaWikiUrl(API_URL) >> false
		interval == ZERO_INTERVAL
	}

	void "when minimal interval is auto, and URL is non-Wikia, zero is returned"() {
		when:
		Long interval = mediaWikiMinimalIntervalConfigurationStrategy.configureInterval(API_URL, VALID_INTERVAL_AUTO)

		then:
		1 * wikiaUrlDetectorMock.isWikiaWikiUrl(API_URL) >> false
		interval == ZERO_INTERVAL
	}

	void "when minimal interval is null, and URL is Wikia, default non-zero interval is returned"() {
		when:
		Long interval = mediaWikiMinimalIntervalConfigurationStrategy.configureInterval(API_URL, null)

		then:
		1 * wikiaUrlDetectorMock.isWikiaWikiUrl(API_URL) >> true
		interval == MediaWikiMinimalIntervalConfigurationStrategy.WIKIA_INTERVAL
	}

	void "when minimal interval is not null, and URL is Wikia, default non-zero interval is returned"() {
		when:
		Long interval = mediaWikiMinimalIntervalConfigurationStrategy.configureInterval(API_URL, VALID_INTERVAL_AUTO)

		then:
		1 * wikiaUrlDetectorMock.isWikiaWikiUrl(API_URL) >> true
		interval == MediaWikiMinimalIntervalConfigurationStrategy.WIKIA_INTERVAL
	}

	void "when minimal interval is not null, it is returned"() {
		when:
		Long interval = mediaWikiMinimalIntervalConfigurationStrategy.configureInterval(API_URL, VALID_INTERVAL_STRING)

		then:
		1 * wikiaUrlDetectorMock.isWikiaWikiUrl(API_URL) >> false
		interval == VALID_INTERVAL_LONG
	}

	void "when minimal interval cannot be parsed, exception is thrown"() {
		when:
		mediaWikiMinimalIntervalConfigurationStrategy.configureInterval(API_URL, INVALID_INTERVAL_STRING)

		then:
		1 * wikiaUrlDetectorMock.isWikiaWikiUrl(API_URL) >> false
		thrown(BeanInitializationException)
	}

}
