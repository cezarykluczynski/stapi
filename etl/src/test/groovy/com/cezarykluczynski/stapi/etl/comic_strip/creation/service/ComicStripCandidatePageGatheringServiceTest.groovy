package com.cezarykluczynski.stapi.etl.comic_strip.creation.service

import com.cezarykluczynski.stapi.sources.mediawiki.converter.PageHeaderConverter
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import spock.lang.Specification

class ComicStripCandidatePageGatheringServiceTest extends Specification {

	private PageHeaderConverter pageHeaderConverterMock

	private ComicStripCandidatePageGatheringService comicStripCandidatePageGatheringService

	void setup() {
		pageHeaderConverterMock = Mock()
		comicStripCandidatePageGatheringService = new ComicStripCandidatePageGatheringService(pageHeaderConverterMock)
	}

	void "adds pages, then clears collection when page headers are requested"() {
		given:
		Page page1 = Mock()
		Page page2 = Mock()
		comicStripCandidatePageGatheringService.addCandidate page1
		comicStripCandidatePageGatheringService.addCandidate page2
		List<PageHeader> pageHeaderList = Mock()

		when:
		List<PageHeader> pageHeaderListOutput = comicStripCandidatePageGatheringService.allPageHeadersThenClean

		then:
		1 * pageHeaderConverterMock.fromPageList(_ as List<Page>) >> { args ->
			List<Page> pageList = (List<Page>) args[0]
			assert pageList.contains(page1)
			assert pageList.contains(page2)
			pageHeaderList
		}
		pageHeaderListOutput == pageHeaderList
		comicStripCandidatePageGatheringService.empty
	}

}
