package com.cezarykluczynski.stapi.etl.movie.creation.processor

import com.cezarykluczynski.stapi.etl.common.service.PageSectionExtractor
import com.cezarykluczynski.stapi.etl.movie.creation.service.MovieExistingEntitiesHelper
import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageSection
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.google.common.collect.Lists
import org.apache.commons.lang3.tuple.Pair
import spock.lang.Specification

class MovieClosingCreditsProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final String CREDITS_FOR_TITLE = 'Credits for TITLE'
	private static final MediaWikiSource MEDIA_WIKI_SOURCE = MediaWikiSource.MEMORY_ALPHA_EN

	private PageApi pageApiMock

	private WikitextApi wikitextApiMock

	private PageSectionExtractor pageSectionExtractorMock

	private MovieExistingEntitiesHelper movieExistingEntitiesHelperMock

	private MovieClosingCreditsProcessor movieClosingCreditsProcessor

	void setup() {
		pageApiMock = Mock()
		wikitextApiMock = Mock()
		pageSectionExtractorMock = Mock()
		movieExistingEntitiesHelperMock = Mock()
		movieClosingCreditsProcessor = new MovieClosingCreditsProcessor(pageApiMock, wikitextApiMock, pageSectionExtractorMock,
				movieExistingEntitiesHelperMock)
	}

	void "returns empty list when credits page cannot be found"() {
		when:
		Pair<List<PageSection>, Page> pair = movieClosingCreditsProcessor.process(new Page())

		then:
		1 * pageApiMock.getPage(*_) >> null
		0 * _
		pair.left.empty
	}

	void "returns empty list when no sections are found"() {
		given:
		Page basePage = new Page(title: TITLE, mediaWikiSource: MEDIA_WIKI_SOURCE)
		Page creditsPage = new Page(title: CREDITS_FOR_TITLE)

		when:
		Pair<List<PageSection>, Page> pair = movieClosingCreditsProcessor.process(basePage)

		then:
		1 * pageApiMock.getPage(CREDITS_FOR_TITLE, MEDIA_WIKI_SOURCE) >> creditsPage
		1 * pageSectionExtractorMock.findByTitles(creditsPage, 'Closing credits', 'Closing Credits', 'Cast', 'Crew') >> Lists.newArrayList()
		0 * _
		pair.left.empty
	}

	void "parses page to list of PageSection"() {
		given:
		Page basePage = new Page(title: TITLE, mediaWikiSource: MEDIA_WIKI_SOURCE)
		Page creditsPage = new Page(title: CREDITS_FOR_TITLE)
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
		Pair<List<PageSection>, Page> pair = movieClosingCreditsProcessor.process(basePage)

		then:
		1 * pageApiMock.getPage(CREDITS_FOR_TITLE, MEDIA_WIKI_SOURCE) >> creditsPage
		1 * pageSectionExtractorMock.findByTitles(creditsPage, 'Closing credits', 'Closing Credits', 'Cast', 'Crew') >> pageSectionList
		1 * wikitextApiMock.getPageLinksFromWikitext('[[Category:Star Trek credits|Voyage Home, The]]') >> []
		2 * wikitextApiMock.getPageLinksFromWikitext('</noinclude>') >> []
		3 * wikitextApiMock.getPageLinksFromWikitext('*Third list item') >> []
		0 * _
		pair.left.size() == 3
		pair.left[0].text == 'Crew'
		pair.left[0].wikitext == '* List Item\n:Another list item'
		pair.left[1].text == 'Closing Credits'
		pair.left[1].wikitext == '* Item in list\n:Item in list 2'
		pair.left[2].text == 'Cast'
		pair.left[2].wikitext == '*Third list item'
	}

	void "parses multiline cast to list of PageSection"() {
		given:
		Page basePage = new Page(title: TITLE, mediaWikiSource: MEDIA_WIKI_SOURCE)
		Page creditsPage = new Page(title: CREDITS_FOR_TITLE)
		List<PageSection> pageSectionList = Lists.newArrayList(
				new PageSection(
						text: 'Cast',
						wikitext: ';[[Jean-Luc Picard]]\n' +
								'* [[Patrick Stewart]]\n' +
								';[[William T. Riker]]\n' +
								'* [[Jonathan Frakes]]\n' +
								';[[Data]] / [[B-4]]\n' +
								'* [[Brent Spiner]]'
				)
		)

		when:
		Pair<List<PageSection>, Page> pair = movieClosingCreditsProcessor.process(basePage)

		then:
		1 * pageApiMock.getPage(CREDITS_FOR_TITLE, MEDIA_WIKI_SOURCE) >> creditsPage
		1 * pageSectionExtractorMock.findByTitles(creditsPage, 'Closing credits', 'Closing Credits', 'Cast', 'Crew') >> pageSectionList

		1 * wikitextApiMock.getPageLinksFromWikitext(';[[Jean-Luc Picard]]') >> [new PageLink(title: 'Jean-Luc Picard')]
		1 * movieExistingEntitiesHelperMock.isAnyKnownCharacter(['[[Jean-Luc Picard]]', 'Jean-Luc Picard']) >> true

		2 * wikitextApiMock.getPageLinksFromWikitext('* [[Patrick Stewart]]') >> [new PageLink(title: 'Patrick Stewart')]
		1 * movieExistingEntitiesHelperMock.isAnyKnownPerformer(['* [[Patrick Stewart]]', 'Patrick Stewart']) >> true

		3 * wikitextApiMock.getPageLinksFromWikitext(';[[William T. Riker]]') >> [new PageLink(title: 'William T. Riker')]
		2 * movieExistingEntitiesHelperMock.isAnyKnownCharacter(['[[William T. Riker]]', 'William T. Riker']) >> true

		4 * wikitextApiMock.getPageLinksFromWikitext('* [[Jonathan Frakes]]') >> [new PageLink(title: 'Jonathan Frakes')]
		3 * movieExistingEntitiesHelperMock.isAnyKnownPerformer(['* [[Jonathan Frakes]]', 'Jonathan Frakes']) >> true

		5 * wikitextApiMock.getPageLinksFromWikitext(';[[Data]] / [[B-4]]') >> [new PageLink(title: 'Data'), new PageLink(title: 'B-4')]
		4 * movieExistingEntitiesHelperMock.isAnyKnownCharacter(['[[Data]] / [[B-4]]', 'Data']) >> true

		6 * wikitextApiMock.getPageLinksFromWikitext('* [[Brent Spiner]]') >> [new PageLink(title: 'Brent Spiner')]
		5 * movieExistingEntitiesHelperMock.isAnyKnownPerformer(['* [[Brent Spiner]]', 'Brent Spiner']) >> true

		0 * _
		pair.left.size() == 1
		pair.left[0].text == 'Cast'
		pair.left[0].wikitext == ';[[Jean-Luc Picard]] &ndash;  [[Patrick Stewart]]\n' +
				';[[William T. Riker]] &ndash;  [[Jonathan Frakes]]\n' +
				';[[Data]] / [[B-4]] &ndash;  [[Brent Spiner]]'
	}

}
