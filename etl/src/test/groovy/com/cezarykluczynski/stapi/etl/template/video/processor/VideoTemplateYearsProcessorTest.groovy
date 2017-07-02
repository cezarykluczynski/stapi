package com.cezarykluczynski.stapi.etl.template.video.processor

import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.YearRange
import com.cezarykluczynski.stapi.etl.template.common.processor.PageLinkToYearProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.YearlinkToYearProcessor
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import org.assertj.core.util.Lists
import spock.lang.Specification

class VideoTemplateYearsProcessorTest extends Specification {

	private static final String WIKITEXT = 'WIKITEXT'

	private WikitextApi wikitextApiMock

	private PageLinkToYearProcessor pageLinkToYearProcessorMock

	private YearlinkToYearProcessor yearlinkToYearProcessorMock

	private VideoTemplateYearsProcessor videoTemplateYearsProcessor

	void setup() {
		wikitextApiMock = Mock()
		pageLinkToYearProcessorMock = Mock()
		yearlinkToYearProcessorMock = Mock()
		videoTemplateYearsProcessor = new VideoTemplateYearsProcessor(wikitextApiMock, pageLinkToYearProcessorMock, yearlinkToYearProcessorMock)
	}

	void "returns range when wikitext contains multiple unordered dates"() {
		given:
		Template.Part templatePart = new Template.Part(value: WIKITEXT)
		List<PageLink> pageLinkList = Lists.newArrayList()
		PageLink pageLink2259 = Mock()
		PageLink pageLink2240s = Mock()
		PageLink pageLink2255 = Mock()
		PageLink pageLink2233 = Mock()
		PageLink pageLink2250 = Mock()
		pageLinkList.add(pageLink2259)
		pageLinkList.add(pageLink2240s)
		pageLinkList.add(pageLink2255)
		pageLinkList.add(pageLink2233)
		pageLinkList.add(pageLink2250)

		when:
		YearRange yearRange = videoTemplateYearsProcessor.process(templatePart)

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(WIKITEXT) >> pageLinkList
		1 * pageLinkToYearProcessorMock.process(pageLink2259) >> 2259
		1 * pageLinkToYearProcessorMock.process(pageLink2240s) >> null
		1 * pageLinkToYearProcessorMock.process(pageLink2255) >> 2255
		1 * pageLinkToYearProcessorMock.process(pageLink2233) >> 2244
		1 * pageLinkToYearProcessorMock.process(pageLink2250) >> 2250
		0 * _
		yearRange.yearFrom == 2244
		yearRange.yearTo == 2259
	}

	void "returns range when wikitext contains single date"() {
		given:
		Template.Part templatePart = new Template.Part(value: WIKITEXT)
		List<PageLink> pageLinkList = Lists.newArrayList()
		PageLink pageLink2259 = Mock()
		pageLinkList.add(pageLink2259)

		when:
		YearRange yearRange = videoTemplateYearsProcessor.process(templatePart)

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(WIKITEXT) >> pageLinkList
		1 * pageLinkToYearProcessorMock.process(pageLink2259) >> 2259
		0 * _
		yearRange.yearFrom == 2259
		yearRange.yearTo == 2259
	}

	void "returns range when templates contains multiple unordered dates"() {
		given:
		Template template2259 = Mock()
		Template template2240s = Mock()
		Template template2255 = Mock()
		Template template2233 = Mock()
		Template template2250 = Mock()
		Template.Part templatePart = new Template.Part(
				value: WIKITEXT,
				templates: Lists.newArrayList(
						template2259,
						template2240s,
						template2255,
						template2233,
						template2250
				))

		when:
		YearRange yearRange = videoTemplateYearsProcessor.process(templatePart)

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(WIKITEXT) >> Lists.newArrayList()
		1 * yearlinkToYearProcessorMock.process(template2259) >> 2259
		1 * yearlinkToYearProcessorMock.process(template2240s) >> null
		1 * yearlinkToYearProcessorMock.process(template2255) >> 2255
		1 * yearlinkToYearProcessorMock.process(template2233) >> 2244
		1 * yearlinkToYearProcessorMock.process(template2250) >> 2250
		0 * _
		yearRange.yearFrom == 2244
		yearRange.yearTo == 2259
	}

	void "returns range when templates contains single date"() {
		given:
		Template template2259 = Mock()
		Template.Part templatePart = new Template.Part(
				value: WIKITEXT,
				templates: Lists.newArrayList(template2259))

		when:
		YearRange yearRange = videoTemplateYearsProcessor.process(templatePart)

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(WIKITEXT) >> Lists.newArrayList()
		1 * yearlinkToYearProcessorMock.process(template2259) >> 2259
		0 * _
		yearRange.yearFrom == 2259
		yearRange.yearTo == 2259
	}

	void "returns null when nothing can be found"() {
		given:
		Template.Part templatePart = new Template.Part(value: WIKITEXT)

		when:
		YearRange yearRange = videoTemplateYearsProcessor.process(templatePart)

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(WIKITEXT) >> Lists.newArrayList()
		0 * _
		yearRange == null
	}

}
