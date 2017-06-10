package com.cezarykluczynski.stapi.etl.magazineSeries.creation.service

import com.cezarykluczynski.stapi.sources.mediawiki.converter.PageHeaderConverter
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import spock.lang.Specification

class MagazineSeriesCandidatePageGatheringServiceTest extends Specification {

	private PageHeaderConverter pageHeaderConverterMock

	private MagazineSeriesCandidatePageGatheringService magazineSeriesCandidatePageGatheringService

	void setup() {
		pageHeaderConverterMock = Mock()
		magazineSeriesCandidatePageGatheringService = new MagazineSeriesCandidatePageGatheringService(pageHeaderConverterMock)
	}

	void "adds pages, then clears collection when page headers are requested"() {
		given:
		Page page1 = Mock()
		Page page2 = Mock()
		magazineSeriesCandidatePageGatheringService.addCandidate page1
		magazineSeriesCandidatePageGatheringService.addCandidate page2
		List<PageHeader> pageHeaderList = Mock()

		when:
		List<PageHeader> pageHeaderListOutput = magazineSeriesCandidatePageGatheringService.allPageHeadersThenClean

		then:
		1 * pageHeaderConverterMock.fromPageList(_ as List<Page>) >> { args ->
			List<Page> pageList = (List<Page>) args[0]
			assert pageList.contains(page1)
			assert pageList.contains(page2)
			pageHeaderList
		}
		pageHeaderListOutput == pageHeaderList
		magazineSeriesCandidatePageGatheringService.empty
	}

}
