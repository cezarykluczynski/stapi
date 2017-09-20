package com.cezarykluczynski.stapi.etl.title.creation.service

import com.cezarykluczynski.stapi.etl.title.creation.processor.list.TitleListPageProcessor
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import spock.lang.Specification

class TitleListCacheTest extends Specification {

	private TitleListPageProcessor titleListPageProcessorMock

	private TitleListCache titleListCache

	void setup() {
		titleListPageProcessorMock = Mock()
		titleListCache = new TitleListCache(titleListPageProcessorMock)
	}

	void "saves pages to cache, then process them, then saves them"() {
		given:
		Page page1 = Mock()
		Page page2 = Mock()
		Page page3 = Mock()

		when:
		titleListCache.add(page1)
		titleListCache.add(page2)
		titleListCache.add(page3)
		titleListCache.produceTitlesFromListPages()

		then:
		1 * titleListPageProcessorMock.process(page1)
		1 * titleListPageProcessorMock.process(page2)
		1 * titleListPageProcessorMock.process(page3)
		0 * _

		when:
		titleListCache.produceTitlesFromListPages()

		then:
		0 * _
	}

}
