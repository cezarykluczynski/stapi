package com.cezarykluczynski.stapi.etl.template.hologram.processor

import com.cezarykluczynski.stapi.etl.template.common.processor.DateStatusProcessor
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.google.common.collect.Lists
import spock.lang.Specification

class HologramActivationDateProcessorTest extends Specification {

	private static final String WIKITEXT = 'WIKITEXT'
	private static final String TITLE_1 = 'TITLE_1'
	private static final String TITLE_2 = 'TITLE_2'
	private static final String TITLE_3 = 'TITLE_3'
	private static final String DATE_STATUS = 'DATE_STATUS'

	private WikitextApi wikitextApiMock

	private DateStatusProcessor dateStatusProcessorMock

	private HologramActivationDateProcessor hologramActivationDateProcessor

	void setup() {
		wikitextApiMock = Mock()
		dateStatusProcessorMock = Mock()
		hologramActivationDateProcessor = new HologramActivationDateProcessor(wikitextApiMock, dateStatusProcessorMock)
	}

	void "gets links from passes wikitext, then returns first non null result from DateStatusProcessor"() {
		when:
		String activationDate = hologramActivationDateProcessor.process(WIKITEXT)

		then:
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT) >> Lists.newArrayList(TITLE_1, TITLE_2, TITLE_3)
		1 * dateStatusProcessorMock.process(TITLE_1) >> null
		1 * dateStatusProcessorMock.process(TITLE_2) >> DATE_STATUS
		0 * _
		activationDate == DATE_STATUS
	}

	void "returns null when no date status can be found"() {
		when:
		String activationDate = hologramActivationDateProcessor.process(WIKITEXT)

		then:
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT) >> Lists.newArrayList(TITLE_1, TITLE_2, TITLE_3)
		1 * dateStatusProcessorMock.process(TITLE_1) >> null
		1 * dateStatusProcessorMock.process(TITLE_2) >> null
		1 * dateStatusProcessorMock.process(TITLE_3) >> null
		0 * _
		activationDate == null
	}

	@SuppressWarnings('ThrowException')
	void "tolerates exception thrown from DateStatusProcessor"() {
		when:
		String activationDate = hologramActivationDateProcessor.process(WIKITEXT)

		then:
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT) >> Lists.newArrayList(TITLE_1, TITLE_2)
		1 * dateStatusProcessorMock.process(TITLE_1) >> { String item ->
			throw new Exception()
		}
		1 * dateStatusProcessorMock.process(TITLE_2) >> DATE_STATUS
		0 * _
		activationDate == DATE_STATUS
	}

}
