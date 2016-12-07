package com.cezarykluczynski.stapi.etl.common.processor

import com.cezarykluczynski.stapi.etl.template.common.linker.EpisodeStaffLinkingWorker
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.model.episode.entity.Episode
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.model.staff.repository.StaffRepository
import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateName
import com.google.common.collect.Lists
import spock.lang.Specification

class EpisodeStaffLinkingWorkerTest extends Specification {

	private static final String WS_WRITTEN_BY_VALUE = 'WS_WRITTEN_BY_VALUE'
	private static final String WS_TELEPLAY_BY_VALUE = 'WS_TELEPLAY_BY_VALUE'
	private static final String WS_STORY_BY_VALUE = 'WS_STORY_BY_VALUE'
	private static final String WS_DIRECTED_BY_VALUE = 'WS_DIRECTED_BY_VALUE'
	private static final String WRITER_NAME = 'WRITER_NAME'
	private static final String TELEPLAY_AUTHOR_NAME = 'TELEPLAY_AUTHOR_NAME'
	private static final String STORY_AUTHOR_NAME = 'STORY_AUTHOR_NAME'
	private static final String DIRECTOR_NAME = 'DIRECTOR_NAME'
	private static final Long STORY_AUTHOR_PAGE_ID = 1L
	private static final Long DIRECTOR_PAGE_ID = 2L

	private WikitextApi wikitextApiMock

	private PageApi pageApiMock

	private StaffRepository staffRepositoryMock

	private TemplateFinder templateFinderMock

	private EpisodeStaffLinkingWorker episodeStaffLinkingWorker

	def setup() {
		wikitextApiMock = Mock(WikitextApi)
		pageApiMock = Mock(PageApi)
		staffRepositoryMock = Mock(StaffRepository)
		templateFinderMock = Mock(TemplateFinder)
		episodeStaffLinkingWorker = new EpisodeStaffLinkingWorker(wikitextApiMock, pageApiMock, staffRepositoryMock,
				templateFinderMock)
	}

	def "does not interact with dependencies other than template finder if sidebar episode template could not be found"() {
		given:
		Page page = new Page(
				templates: Lists.newArrayList()
		)

		when:
		episodeStaffLinkingWorker.link(page, new Episode())

		then:
		templateFinderMock.findTemplate(page, TemplateName.SIDEBAR_EPISODE) >> Optional.empty()
		0 * _
	}

	def "gets staff from template parts"() {
		given:
		Template sidebarEpisodeTemplate = new Template(
				title: TemplateName.SIDEBAR_EPISODE,
				parts: Lists.newArrayList(
						new Template.Part(
								key: EpisodeStaffLinkingWorker.WS_WRITTEN_BY,
								value: WS_WRITTEN_BY_VALUE
						),
						new Template.Part(
								key: EpisodeStaffLinkingWorker.WS_TELEPLAY_BY,
								value: WS_TELEPLAY_BY_VALUE
						),
						new Template.Part(
								key: EpisodeStaffLinkingWorker.WS_STORY_BY,
								value: WS_STORY_BY_VALUE
						),
						new Template.Part(
								key: EpisodeStaffLinkingWorker.WS_DIRECTED_BY,
								value: WS_DIRECTED_BY_VALUE
						)
				)
		)
		Episode episode = new Episode()
		Page page = new Page(
				templates: Lists.newArrayList(
						sidebarEpisodeTemplate
				)
		)
		Staff writer = Mock(Staff)
		Staff teleplayAuthor = Mock(Staff)
		Staff storyAuthor = Mock(Staff)
		Staff director = Mock(Staff)

		when:
		episodeStaffLinkingWorker.link(page, episode)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateName.SIDEBAR_EPISODE) >> Optional.of(sidebarEpisodeTemplate)

		then: 'gets writer from repository'
		1 * wikitextApiMock.getPageLinksFromWikitext(WS_WRITTEN_BY_VALUE) >> Lists.newArrayList(new PageLink(title: WRITER_NAME))
		1 * staffRepositoryMock.findByName(WRITER_NAME) >> Optional.of(writer)

		then: 'gets teleplay author from repository'
		1 * wikitextApiMock.getPageLinksFromWikitext(WS_TELEPLAY_BY_VALUE) >> Lists.newArrayList(new PageLink(title: TELEPLAY_AUTHOR_NAME))
		1 * staffRepositoryMock.findByName(TELEPLAY_AUTHOR_NAME) >> Optional.of(teleplayAuthor)

		then: 'gets story author from MediaWiki API, when from repository, by page ID'
		1 * wikitextApiMock.getPageLinksFromWikitext(WS_STORY_BY_VALUE) >> Lists.newArrayList(new PageLink(title: STORY_AUTHOR_NAME))
		1 * staffRepositoryMock.findByName(STORY_AUTHOR_NAME) >> Optional.empty()
		1 * pageApiMock.getPage(STORY_AUTHOR_NAME, EpisodeStaffLinkingWorker.SOURCE) >> new Page(pageId: STORY_AUTHOR_PAGE_ID)
		1 * staffRepositoryMock.findByPagePageId(STORY_AUTHOR_PAGE_ID) >> Optional.of(storyAuthor)

		then: 'gets director from MediaWiki API, when from repository, by page ID'
		1 * wikitextApiMock.getPageLinksFromWikitext(WS_DIRECTED_BY_VALUE) >> Lists.newArrayList(new PageLink(title: DIRECTOR_NAME))
		1 * staffRepositoryMock.findByName(DIRECTOR_NAME) >> Optional.empty()
		1 * pageApiMock.getPage(DIRECTOR_NAME, EpisodeStaffLinkingWorker.SOURCE) >> new Page(pageId: DIRECTOR_PAGE_ID)
		1 * staffRepositoryMock.findByPagePageId(DIRECTOR_PAGE_ID) >> Optional.of(director)

		then: 'episode has staff set'
		episode.writers.contains writer
		episode.teleplayAuthors.contains teleplayAuthor
		episode.storyAuthors.contains storyAuthor
		episode.directors.contains director

		then: 'no other interactions are expected'
		0 * _
	}

}
