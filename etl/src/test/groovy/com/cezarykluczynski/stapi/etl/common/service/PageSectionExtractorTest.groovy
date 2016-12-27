package com.cezarykluczynski.stapi.etl.common.service

import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageSection
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.google.common.collect.Lists
import spock.lang.Specification

class PageSectionExtractorTest extends Specification {

	private static final String TEXT = 'TEXT'

	private PageSectionExtractor pageSectionExtractor

	def setup() {
		pageSectionExtractor = new PageSectionExtractor()
	}

	def "extract last page section"() {
		given:
		PageSection lastPageSection = new PageSection(
				byteOffset: 500,
				wikitext: ''
		)
		Page page = new Page(
				sections: Lists.newArrayList(
						new PageSection(
								byteOffset: 300,
								wikitext: ''
						),
						lastPageSection,
						new PageSection(
								byteOffset: 100,
								wikitext: ''
						),
				)
		)

		when:
		PageSection pageSection = pageSectionExtractor.extractLastPageSection(page)

		then:
		pageSection == lastPageSection
	}

	def "returns null when getting last page section, but page section list is empty"() {
		given:
		Page page = new Page()

		when:
		PageSection pageSection = pageSectionExtractor.extractLastPageSection(page)

		then:
		pageSection == null
	}

	def "gets section by title"() {
		given:
		PageSection pageSectionToFind = new PageSection(
				text: TEXT
		)
		Page page = new Page(
				sections: Lists.newArrayList(
						new PageSection(),
						pageSectionToFind,
						new PageSection(),
				)
		)

		when:
		List<PageSection> pageSectionList = pageSectionExtractor.findByTitles(page, TEXT)

		then:
		pageSectionList[0] == pageSectionToFind
	}

	def "returns null when section cannot be found by title"() {
		given:
		Page page = new Page(sections: Lists.newArrayList())

		when:
		List<PageSection> pageSectionList = pageSectionExtractor.findByTitles(page, TEXT)

		then:
		pageSectionList[0] == null
	}

	def "requires parameters not to be null when section is searched for by tile"() {
		when:
		pageSectionExtractor.findByTitles(new Page(), null)

		then:
		thrown(NullPointerException)

		when:
		pageSectionExtractor.findByTitles(null, TEXT)

		then:
		thrown(NullPointerException)
	}

}
