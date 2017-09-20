package com.cezarykluczynski.stapi.etl.title.creation.processor.list

import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageSection
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import com.google.common.collect.Lists
import spock.lang.Specification

class TitleListPageProcessorTest extends Specification {

	private TitleListSectionProcessor titleListSectionProcessorMock

	private TitleListMacoMirrorProcessor titleListMacoMirrorProcessorMock

	private TitleListPageProcessor titleListPageProcessor

	void setup() {
		titleListSectionProcessorMock = Mock()
		titleListMacoMirrorProcessorMock = Mock()
		titleListPageProcessor = new TitleListPageProcessor(titleListSectionProcessorMock, titleListMacoMirrorProcessorMock)
	}

	void "parses page"() {
		given:

		PageSection pageSectionToFilterOut1 = createPageSection(2, null)
		PageSection pageSectionToFilterOut2 = createPageSection(3, RandomUtil.randomItem(TitleListPageProcessor.PAGE_SECTIONS_TO_FILTER_OUT))
		PageSection pageSection1 = createPageSection(3, 'title 1')
		PageSection pageSection2 = createPageSection(3, TitleListPageProcessor.MACOS)
		Page page = new Page(
				title: 'Klingon ranks',
				sections: Lists.newArrayList(pageSectionToFilterOut1, pageSectionToFilterOut2, pageSection1, pageSection2)
		)

		when:
		titleListPageProcessor.process(page)

		then:
		1 * titleListSectionProcessorMock.process(page, pageSection1, 'Klingon', 0)
		1 * titleListMacoMirrorProcessorMock.produceAll(page, 1)
		0 * _
	}

	private static PageSection createPageSection(int level, String text) {
		new PageSection(level: level, text: text)
	}

}
