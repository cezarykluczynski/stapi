package com.cezarykluczynski.stapi.etl.season.creation.processor

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor
import com.cezarykluczynski.stapi.model.season.entity.Season
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import spock.lang.Specification

class SeasonProcessorTest extends Specification {

	private PageHeaderProcessor pageHeaderProcessorMock

	private SeasonPageProcessor seasonPageProcessorMock

	private SeasonProcessor seasonProcessor

	void setup() {
		pageHeaderProcessorMock = Mock()
		seasonPageProcessorMock = Mock()
		seasonProcessor = new SeasonProcessor(pageHeaderProcessorMock, seasonPageProcessorMock)
	}

	void "converts PageHeader to Season"() {
		given:
		PageHeader pageHeader = new PageHeader()
		Page page = new Page()
		Season season = new Season()

		when:
		Season seasonOutput = seasonProcessor.process(pageHeader)

		then: 'processors are used in right order'
		1 * pageHeaderProcessorMock.process(pageHeader) >> page

		and:
		1 * seasonPageProcessorMock.process(page) >> season

		then: 'last processor output is returned'
		seasonOutput == season
	}

}
