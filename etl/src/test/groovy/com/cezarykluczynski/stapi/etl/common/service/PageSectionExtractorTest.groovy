package com.cezarykluczynski.stapi.etl.common.service

import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageSection
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.google.common.collect.Lists
import spock.lang.Specification

class PageSectionExtractorTest extends Specification {

	private static final String TEXT_1 = 'TEXT_1'
	private static final String TEXT_2 = 'TEXT_2'
	private static final String TEXT_2_LOWER_CASE = 'text_2'
	private static final String TEXT_3 = 'TEXT_3'
	private static final String TEXT_3_LOWER_CASE = 'text_3'

	private PageSectionExtractor pageSectionExtractor

	void setup() {
		pageSectionExtractor = new PageSectionExtractor()
	}

	void "extract last page section"() {
		given:
		PageSection lastPageSection = new PageSection(
				byteOffset: 500,
				wikitext: ''
		)
		Page page = new Page(sections: Lists.newArrayList(
				new PageSection(
						byteOffset: 300,
						wikitext: ''
				),
				lastPageSection,
				new PageSection(
						byteOffset: 100,
						wikitext: ''
				),
		))

		when:
		PageSection pageSection = pageSectionExtractor.extractLastPageSection(page)

		then:
		pageSection == lastPageSection
	}

	void "returns null when getting last page section, but page section list is empty"() {
		given:
		Page page = new Page()

		when:
		PageSection pageSection = pageSectionExtractor.extractLastPageSection(page)

		then:
		pageSection == null
	}

	void "gets section by title"() {
		given:
		PageSection pageSectionToFind = new PageSection(
				text: TEXT_1
		)
		Page page = new Page(sections: Lists.newArrayList(
				new PageSection(),
				pageSectionToFind,
				new PageSection(),
		))

		when:
		List<PageSection> pageSectionList = pageSectionExtractor.findByTitles(page, TEXT_1)

		then:
		pageSectionList[0] == pageSectionToFind
	}

	void "returns empty list when section cannot be found by title"() {
		given:
		Page page = new Page(sections: Lists.newArrayList())

		when:
		List<PageSection> pageSectionList = pageSectionExtractor.findByTitles(page, TEXT_1)

		then:
		pageSectionList.empty
	}

	void "requires parameters not to be null when section is searched for by tile"() {
		when:
		pageSectionExtractor.findByTitles(new Page(), null)

		then:
		thrown(NullPointerException)

		when:
		pageSectionExtractor.findByTitles(null, TEXT_1)

		then:
		thrown(NullPointerException)
	}

	void "gets sections by title, including subsections, ignoring case"() {
		given:
		PageSection rootPageSection1 = new PageSection(level: 1, text: TEXT_1, byteOffset: 100)
		PageSection childPageSection1 = new PageSection(level: 2, byteOffset: 200)
		PageSection childPageSection2 = new PageSection(level: 2, byteOffset: 300)
		PageSection grandchildPageSection1 = new PageSection(level: 3, byteOffset: 400)
		PageSection rootPageSection2 = new PageSection(level: 1, text: TEXT_2_LOWER_CASE, byteOffset: 500)
		PageSection childPageSection3 = new PageSection(level: 2, text: TEXT_3, byteOffset: 600)
		PageSection grandchildPageSection2 = new PageSection(level: 3, byteOffset: 700)
		PageSection childPageSection4 = new PageSection(level: 2, byteOffset: 800)
		Page page = new Page(sections: Lists.newArrayList(
				rootPageSection1,
				childPageSection1,
				childPageSection2,
				grandchildPageSection1,
				new PageSection(level: 1),
				rootPageSection2,
				childPageSection3,
				grandchildPageSection2,
				childPageSection4
		))

		when:
		List<PageSection> pageSectionList = pageSectionExtractor.findByTitlesIncludingSubsections(page, TEXT_1, TEXT_2, TEXT_3_LOWER_CASE)

		then:
		pageSectionList.size() == 8
		pageSectionList[0] == rootPageSection1
		pageSectionList[1] == childPageSection1
		pageSectionList[2] == childPageSection2
		pageSectionList[3] == grandchildPageSection1
		pageSectionList[4] == rootPageSection2
		pageSectionList[5] == childPageSection3
		pageSectionList[6] == grandchildPageSection2
		pageSectionList[7] == childPageSection4
	}

	void "returns empty list when section cannot be found by title, including subsections"() {
		given:
		Page page = new Page(sections: Lists.newArrayList())

		when:
		List<PageSection> pageSectionList = pageSectionExtractor.findByTitlesIncludingSubsections(page, TEXT_1)

		then:
		pageSectionList.empty
	}

}
