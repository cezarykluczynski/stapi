package com.cezarykluczynski.stapi.etl.movie.creation.processor

import com.cezarykluczynski.stapi.etl.common.service.PageSectionExtractor
import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageSection
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.google.common.collect.Lists
import spock.lang.Specification

class MovieClosingCreditsProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final MediaWikiSource MEDIA_WIKI_SOURCE = MediaWikiSource.MEMORY_ALPHA_EN

	private PageApi pageApiMock

	private PageSectionExtractor pageSectionExtractorMock

	private MovieClosingCreditsProcessor movieClosingCreditsProcessor

	def setup() {
		pageApiMock = Mock(PageApi)
		pageSectionExtractorMock = Mock(PageSectionExtractor)
		movieClosingCreditsProcessor = new MovieClosingCreditsProcessor(pageApiMock, pageSectionExtractorMock)
	}

	def "returns empty list when credits page cannot be found"() {
		when:
		List<PageSection> pageSectionList = movieClosingCreditsProcessor.process(new Page())

		then:
		1 * pageApiMock.getPage(*_) >> null
		0 * _
		pageSectionList.empty
	}

	def "returns empty list when no sections are found"() {
		given:
		Page basePage = Mock(Page)
		Page creditsPage = Mock(Page)

		when:
		List<PageSection> pageSectionList = movieClosingCreditsProcessor.process(basePage)

		then:
		1 * pageApiMock.getPage("Credits for " + TITLE, MEDIA_WIKI_SOURCE) >> creditsPage
		1 * basePage.getTitle() >> TITLE
		1 * basePage.getMediaWikiSource() >> MEDIA_WIKI_SOURCE
		1 * pageSectionExtractorMock.findByTitles(creditsPage, "Closing credits", "Closing Credits", "Cast", "Crew") >> Lists.newArrayList()
		0 * _
		pageSectionList.empty
	}

	def "parses page to list of PageSection"() {
		given:
		Page basePage = Mock(Page)
		Page creditsPage = Mock(Page)
		List<PageSection> pageSectionList = Lists.newArrayList(
				new PageSection(
						text: 'Crew',
						wikitext: '* List Item\n:Another list item\nNot a list item\n[[es:Nope]]\n'
								+ ';Closing Credits\nNot a list item\n* Item in list\n:Item in list 2\n<noinclude>'
				),
				new PageSection(
						text: 'Cast',
						wikitext: '[[Category:Star Trek credits|Voyage Home, The]]\n</noinclude>\n'
								+ '*Third list item'
				)
		)

		when:
		List<PageSection> pageSectionListOutput = movieClosingCreditsProcessor.process(basePage)

		then:
		1 * pageApiMock.getPage("Credits for " + TITLE, MEDIA_WIKI_SOURCE) >> creditsPage
		1 * basePage.getTitle() >> TITLE
		1 * basePage.getMediaWikiSource() >> MEDIA_WIKI_SOURCE
		1 * pageSectionExtractorMock.findByTitles(creditsPage, "Closing credits", "Closing Credits", "Cast", "Crew") >> pageSectionList
		0 * _
		pageSectionListOutput.size() == 3
		pageSectionListOutput[0].text == 'Crew'
		pageSectionListOutput[0].wikitext == '* List Item\n:Another list item'
		pageSectionListOutput[1].text == 'Closing Credits'
		pageSectionListOutput[1].wikitext == '* Item in list\n:Item in list 2'
		pageSectionListOutput[2].text == 'Cast'
		pageSectionListOutput[2].wikitext == '*Third list item'
	}

}
