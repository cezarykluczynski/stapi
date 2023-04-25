package com.cezarykluczynski.stapi.etl.movie.creation.processor

import com.cezarykluczynski.stapi.etl.movie.creation.dto.MovieLinkedTitlesDTO
import com.cezarykluczynski.stapi.etl.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.etl.mediawiki.api.dto.PageSection
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page
import com.google.common.collect.Lists
import org.apache.commons.lang3.tuple.Pair
import spock.lang.Specification

class MovieLinkedTitlesProcessorTest extends Specification {

	private static final String IGNORABLE_SECTION_TITLE = MovieLinkedTitlesProcessor.IGNORABLE_SECTION_EXACT_TITLE_LIST[0]
	private static final String IGNORABLE_SECTION_WIKITEXT = 'IGNORABLE_SECTION_WIKITEXT'
	private static final String UNKNOWN_SECTION_TITLE = 'UNKNOWN_SECTION_TITLE'
	private static final String UNKNOWN_SECTION_WIKITEXT = 'UNKNOWN_SECTION_WIKITEXT'
	private static final String WRITERS_SECTION_TITLE = MovieLinkedTitlesProcessor.WRITERS_SECTION_EXACT_TITLE_LIST[0]
	private static final String WRITERS_WIKITEXT = 'WRITERS_WIKITEXT'
	private static final String WRITERS_LINK = 'WRITERS_LINK'
	private static final String SCREENPLAY_AUTHORS_SECTION_TITLE = MovieLinkedTitlesProcessor.SCREENPLAY_AUTHORS_SECTION_EXACT_TITLE_LIST[0]
	private static final String SCREENPLAY_AUTHORS_WIKITEXT = 'SCREENPLAY_AUTHORS_WIKITEXT'
	private static final String SCREENPLAY_AUTHORS_LINK = 'SCREENPLAY_AUTHORS_LINK'
	private static final String STORY_AUTHORS_SECTION_TITLE = MovieLinkedTitlesProcessor.STORY_AUTHORS_SECTION_EXACT_TITLE_LIST[0]
	private static final String STORY_AUTHORS_WIKITEXT = 'STORY_AUTHORS_WIKITEXT'
	private static final String STORY_AUTHORS_LINK = 'STORY_AUTHORS_LINK'
	private static final String DIRECTORS_SECTION_TITLE = MovieLinkedTitlesProcessor.DIRECTORS_SECTION_EXACT_TITLE_LIST[0]
	private static final String DIRECTORS_SECTION_MATCH = MovieLinkedTitlesProcessor.DIRECTORS_SECTION_MATHCES_TITLE_LIST[0]
	private static final String DIRECTORS_WIKITEXT = 'DIRECTORS_WIKITEXT'
	private static final String DIRECTORS_LINK = 'DIRECTORS_LINK'
	private static final String PRODUCERS_SECTION_TITLE = MovieLinkedTitlesProcessor.PRODUCERS_SECTION_EXACT_TITLE_LIST[0]
	private static final String PRODUCERS_WIKITEXT = 'PRODUCERS_WIKITEXT'
	private static final String PRODUCERS_LINK = 'PRODUCERS_LINK'
	private static final String STAFF_SECTION_TITLE = MovieLinkedTitlesProcessor.STAFF_SECTION_EXACT_TITLE_LIST[0]
	private static final String STAFF_SECTION_MATCH = MovieLinkedTitlesProcessor.STAFF_SECTION_MATCHES_TITLE_LIST[0]
	private static final String STAFF_WIKITEXT = 'STAFF_WIKITEXT'
	private static final String STAFF_LINK = 'STAFF_LINK'
	private static final String PERFORMERS_SECTION_TITLE = MovieLinkedTitlesProcessor.PERFORMERS_SECTION_EXACT_TITLE_LIST[0]
	private static final String PERFORMERS_WIKITEXT = 'PERFORMERS_WIKITEXT'
	private static final String PERFORMERS_LINK = 'PERFORMERS_LINK'
	private static final String STUNT_PERFORMERS_SECTION_TITLE = MovieLinkedTitlesProcessor.STUNT_PERFORMERS_SECTION_EXACT_TITLE_LIST[0]
	private static final String STUNT_PERFORMERS_MATCH_TITLE = MovieLinkedTitlesProcessor.STUNT_PERFORMERS_SECTION_MATCHES_TITLE_LIST[0]
	private static final String STUNT_PERFORMERS_WIKITEXT = 'STUNT_PERFORMERS_WIKITEXT'
	private static final String STUNT_PERFORMERS_LINK = 'STUNT_PERFORMERS_LINK'
	private static final String STAND_IN_PERFORMERS_SECTION_TITLE = MovieLinkedTitlesProcessor.STAND_IN_PERFORMERS_SECTION_EXACT_TITLE_LIST[0]
	private static final String STAND_IN_PERFORMERS_MATCH_TITLE = MovieLinkedTitlesProcessor.STAND_IN_PERFORMERS_SECTION_MATCHES_TITLE_LIST[0]
	private static final String STAND_IN_PERFORMERS_WIKITEXT = 'STAND_IN_PERFORMERS_WIKITEXT'
	private static final String STAND_IN_PERFORMERS_LINK = 'STAND_IN_PERFORMERS_LINK'
	private static final String TITLE = 'TITLE'

	private WikitextApi wikitextApiMock
	private MovieLinkedTitlesProcessor movieLinkedTitlesProcessor

	void setup() {
		wikitextApiMock = Mock()
		movieLinkedTitlesProcessor = new MovieLinkedTitlesProcessor(wikitextApiMock)
	}

	void "tolerates empty PageSection list"() {
		when:
		MovieLinkedTitlesDTO movieLinkedTitlesDTO = movieLinkedTitlesProcessor.process(Pair.of(Lists.newArrayList(), null))

		then:
		movieLinkedTitlesDTO.screenplayAuthors.empty
		movieLinkedTitlesDTO.storyAuthors.empty
		movieLinkedTitlesDTO.directors.empty
		movieLinkedTitlesDTO.producers.empty
		movieLinkedTitlesDTO.staff.empty
		movieLinkedTitlesDTO.performers.empty
		movieLinkedTitlesDTO.stuntPerformers.empty
		movieLinkedTitlesDTO.standInPerformers.empty
	}

	void "get titles in sections by exact titles"() {
		given:
		PageSection writersPageSection = createPageSection(WRITERS_SECTION_TITLE, WRITERS_WIKITEXT)
		PageSection ignorablePageSection = createPageSection(IGNORABLE_SECTION_TITLE, IGNORABLE_SECTION_WIKITEXT)
		PageSection unknownPageSection = createPageSection(UNKNOWN_SECTION_TITLE, UNKNOWN_SECTION_WIKITEXT)
		PageSection screenplayAuthorsPageSection = createPageSection(SCREENPLAY_AUTHORS_SECTION_TITLE, SCREENPLAY_AUTHORS_WIKITEXT)
		PageSection storyAuthorsPageSection = createPageSection(STORY_AUTHORS_SECTION_TITLE + ':', STORY_AUTHORS_WIKITEXT)
		PageSection directorsPageSection = createPageSection(DIRECTORS_SECTION_TITLE, DIRECTORS_WIKITEXT)
		PageSection producersPageSection = createPageSection(PRODUCERS_SECTION_TITLE, PRODUCERS_WIKITEXT)
		PageSection staffPageSection = createPageSection(STAFF_SECTION_TITLE, STAFF_WIKITEXT)
		PageSection performersPageSection = createPageSection(PERFORMERS_SECTION_TITLE, PERFORMERS_WIKITEXT)
		PageSection stuntPerformersPageSection = createPageSection(STUNT_PERFORMERS_SECTION_TITLE, STUNT_PERFORMERS_WIKITEXT)
		PageSection standInPerformersPageSection = createPageSection(STAND_IN_PERFORMERS_SECTION_TITLE, STAND_IN_PERFORMERS_WIKITEXT)
		List<PageSection> pageSectionList = Lists.newArrayList(writersPageSection, ignorablePageSection,
				unknownPageSection, screenplayAuthorsPageSection, storyAuthorsPageSection, directorsPageSection,
				producersPageSection, staffPageSection, performersPageSection, stuntPerformersPageSection,
				standInPerformersPageSection)
		Page page = Mock()

		when:
		MovieLinkedTitlesDTO movieLinkedTitlesDTO = movieLinkedTitlesProcessor.process(Pair.of(pageSectionList, page))

		then:
		1 * wikitextApiMock.getPageTitlesFromWikitextExcludingNotFound(WRITERS_WIKITEXT, page) >> Lists.newArrayList(WRITERS_LINK)
		movieLinkedTitlesDTO.writers.size() == 1
		movieLinkedTitlesDTO.writers[0][0] == WRITERS_LINK
		1 * wikitextApiMock.getPageTitlesFromWikitextExcludingNotFound(IGNORABLE_SECTION_WIKITEXT, page) >> Lists.newArrayList()
		1 * wikitextApiMock.getPageTitlesFromWikitextExcludingNotFound(UNKNOWN_SECTION_WIKITEXT, page) >> Lists.newArrayList()
		1 * page.title >> TITLE
		1 * wikitextApiMock.getPageTitlesFromWikitextExcludingNotFound(SCREENPLAY_AUTHORS_WIKITEXT, page) >> Lists.newArrayList(SCREENPLAY_AUTHORS_LINK)
		movieLinkedTitlesDTO.screenplayAuthors.size() == 1
		movieLinkedTitlesDTO.screenplayAuthors[0][0] == SCREENPLAY_AUTHORS_LINK
		1 * wikitextApiMock.getPageTitlesFromWikitextExcludingNotFound(STORY_AUTHORS_WIKITEXT, page) >> Lists.newArrayList(STORY_AUTHORS_LINK)
		movieLinkedTitlesDTO.storyAuthors.size() == 1
		movieLinkedTitlesDTO.storyAuthors[0][0] == STORY_AUTHORS_LINK
		1 * wikitextApiMock.getPageTitlesFromWikitextExcludingNotFound(DIRECTORS_WIKITEXT, page) >> Lists.newArrayList(DIRECTORS_LINK)
		movieLinkedTitlesDTO.directors.size() == 1
		movieLinkedTitlesDTO.directors[0][0] == DIRECTORS_LINK
		1 * wikitextApiMock.getPageTitlesFromWikitextExcludingNotFound(PRODUCERS_WIKITEXT, page) >> Lists.newArrayList(PRODUCERS_LINK)
		movieLinkedTitlesDTO.producers.size() == 1
		movieLinkedTitlesDTO.producers[0][0] == PRODUCERS_LINK
		1 * wikitextApiMock.getPageTitlesFromWikitextExcludingNotFound(STAFF_WIKITEXT, page) >> Lists.newArrayList(STAFF_LINK)
		movieLinkedTitlesDTO.staff.size() == 1
		movieLinkedTitlesDTO.staff[0][0] == STAFF_LINK
		1 * wikitextApiMock.getPageTitlesFromWikitextExcludingNotFound(PERFORMERS_WIKITEXT, page) >> Lists.newArrayList(PERFORMERS_LINK)
		movieLinkedTitlesDTO.performers.size() == 1
		movieLinkedTitlesDTO.performers[0][0] == PERFORMERS_LINK
		1 * wikitextApiMock.getPageTitlesFromWikitextExcludingNotFound(STUNT_PERFORMERS_WIKITEXT, page) >> Lists.newArrayList(STUNT_PERFORMERS_LINK)
		movieLinkedTitlesDTO.stuntPerformers.size() == 1
		movieLinkedTitlesDTO.stuntPerformers[0][0] == STUNT_PERFORMERS_LINK
		1 * wikitextApiMock.getPageTitlesFromWikitextExcludingNotFound(STAND_IN_PERFORMERS_WIKITEXT, page) >> Lists.newArrayList(STAND_IN_PERFORMERS_LINK)
		movieLinkedTitlesDTO.standInPerformers.size() == 1
		movieLinkedTitlesDTO.standInPerformers[0][0] == STAND_IN_PERFORMERS_LINK
		0 * _
	}

	void "gets titles in sections by matching titles"() {
		given:
		PageSection directorsPageSection = createPageSection(DIRECTORS_SECTION_MATCH, DIRECTORS_WIKITEXT)
		PageSection staffPageSection = createPageSection(STAFF_SECTION_MATCH, STAFF_WIKITEXT)
		PageSection stuntPerformersPageSection = createPageSection(STUNT_PERFORMERS_MATCH_TITLE, STUNT_PERFORMERS_WIKITEXT)
		PageSection standInPerformersPageSection = createPageSection(STAND_IN_PERFORMERS_MATCH_TITLE, STAND_IN_PERFORMERS_WIKITEXT)
		List<PageSection> pageSectionList = Lists.newArrayList(directorsPageSection, staffPageSection,
				stuntPerformersPageSection, standInPerformersPageSection)
		Page page = Mock()

		when:
		MovieLinkedTitlesDTO movieLinkedTitlesDTO = movieLinkedTitlesProcessor.process(Pair.of(pageSectionList, page))

		then:
		1 * wikitextApiMock.getPageTitlesFromWikitextExcludingNotFound(DIRECTORS_WIKITEXT, page) >> Lists.newArrayList(DIRECTORS_LINK)
		movieLinkedTitlesDTO.directors.size() == 1
		movieLinkedTitlesDTO.directors[0][0] == DIRECTORS_LINK
		1 * wikitextApiMock.getPageTitlesFromWikitextExcludingNotFound(STAFF_WIKITEXT, page) >> Lists.newArrayList(STAFF_LINK)
		movieLinkedTitlesDTO.staff.size() == 1
		movieLinkedTitlesDTO.staff[0][0] == STAFF_LINK
		1 * wikitextApiMock.getPageTitlesFromWikitextExcludingNotFound(STUNT_PERFORMERS_WIKITEXT, page) >> Lists.newArrayList(STUNT_PERFORMERS_LINK)
		movieLinkedTitlesDTO.stuntPerformers.size() == 1
		movieLinkedTitlesDTO.stuntPerformers[0][0] == STUNT_PERFORMERS_LINK
		1 * wikitextApiMock.getPageTitlesFromWikitextExcludingNotFound(STAND_IN_PERFORMERS_WIKITEXT, page) >> Lists.newArrayList(STAND_IN_PERFORMERS_LINK)
		movieLinkedTitlesDTO.standInPerformers.size() == 1
		movieLinkedTitlesDTO.standInPerformers[0][0] == STAND_IN_PERFORMERS_LINK
		0 * _
	}

	private static PageSection createPageSection(String text, String wikitext) {
		new PageSection(
				text: text,
				wikitext: wikitext)
	}

}
