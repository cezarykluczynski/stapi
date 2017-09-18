package com.cezarykluczynski.stapi.etl.title.creation.service

import com.cezarykluczynski.stapi.etl.title.creation.processor.TitleListPageProcessor
import com.cezarykluczynski.stapi.etl.title.creation.processor.TitleWriter
import com.cezarykluczynski.stapi.model.title.entity.Title
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.google.common.collect.Lists
import spock.lang.Specification

class TitleListCacheTest extends Specification {

	private TitleListPageProcessor titleListPageProcessorMock

	private TitleWriter titleWriterMock

	private TitleListCache titleListCache

	void setup() {
		titleListPageProcessorMock = Mock()
		titleWriterMock = Mock()
		titleListCache = new TitleListCache(titleListPageProcessorMock, titleWriterMock)
	}

	void "saves pages to cache, then process them, then saves them"() {
		given:
		Page page1 = Mock()
		Page page2 = Mock()
		Page page3 = Mock()
		Title title1 = Mock()
		Title title2 = Mock()
		Title title3 = Mock()

		when:
		titleListCache.add(page1)
		titleListCache.add(page2)
		titleListCache.add(page3)
		titleListCache.produceTitlesFromListPages()

		then:
		1 * titleListPageProcessorMock.process(page1) >> Lists.newArrayList(title1, title2)
		1 * titleListPageProcessorMock.process(page2) >> Lists.newArrayList(title3)
		1 * titleListPageProcessorMock.process(page3) >> Lists.newArrayList()
		1 * titleWriterMock.write(Lists.newArrayList(title1, title2, title3))
		0 * _

		when:
		titleListCache.produceTitlesFromListPages()

		then:
		1 * titleWriterMock.write(Lists.newArrayList())
		0 * _
	}

}
