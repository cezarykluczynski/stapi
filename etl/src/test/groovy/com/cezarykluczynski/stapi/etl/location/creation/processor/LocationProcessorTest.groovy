package com.cezarykluczynski.stapi.etl.location.creation.processor

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor
import com.cezarykluczynski.stapi.model.location.entity.Location
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import spock.lang.Specification

class LocationProcessorTest extends Specification {

	private PageHeaderProcessor pageHeaderProcessorMock

	private LocationPageProcessor locationPageProcessorMock

	private LocationProcessor locationProcessor

	void setup() {
		pageHeaderProcessorMock = Mock()
		locationPageProcessorMock = Mock()
		locationProcessor = new LocationProcessor(pageHeaderProcessorMock, locationPageProcessorMock)
	}

	void "converts PageHeader to Location"() {
		given:
		PageHeader pageHeader = new PageHeader()
		Page page = new Page()
		Location location = new Location()

		when:
		Location locationOutput = locationProcessor.process(pageHeader)

		then: 'processors are used in right order'
		1 * pageHeaderProcessorMock.process(pageHeader) >> page

		and:
		1 * locationPageProcessorMock.process(page) >> location

		then: 'last processor output is returned'
		locationOutput == location
	}

}
