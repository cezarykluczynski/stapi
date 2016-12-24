package com.cezarykluczynski.stapi.etl.common.service

import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageSection
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.google.common.collect.Lists
import spock.lang.Specification

class PageSectionExtractorTest extends Specification {

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
		PageSection pagesection = pageSectionExtractor.extractLastPageSection(page)

		then:
		pagesection == lastPageSection
	}

	def "returns null when page section list is empty"() {
		given:
		Page page = new Page()

		when:
		PageSection pagesection = pageSectionExtractor.extractLastPageSection(page)

		then:
		pagesection == null
	}

}
