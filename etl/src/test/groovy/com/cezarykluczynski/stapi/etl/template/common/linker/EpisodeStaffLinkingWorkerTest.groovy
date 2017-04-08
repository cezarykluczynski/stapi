package com.cezarykluczynski.stapi.etl.template.common.linker

import com.cezarykluczynski.stapi.etl.common.service.EntityLookupByNameService
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.model.episode.entity.Episode
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
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

	private WikitextApi wikitextApiMock

	private EntityLookupByNameService entityLookupByNameServiceMock

	private TemplateFinder templateFinderMock

	private EpisodeStaffLinkingWorker episodeStaffLinkingWorker

	void setup() {
		wikitextApiMock = Mock()
		entityLookupByNameServiceMock = Mock()
		templateFinderMock = Mock()
		episodeStaffLinkingWorker = new EpisodeStaffLinkingWorker(wikitextApiMock, entityLookupByNameServiceMock, templateFinderMock)
	}

	void "does not interact with dependencies other than template finder if sidebar episode template could not be found"() {
		given:
		Page page = new Page(templates: Lists.newArrayList())

		when:
		episodeStaffLinkingWorker.link(page, new Episode())

		then:
		templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_EPISODE) >> Optional.empty()
		0 * _
	}

	void "gets staff from template parts"() {
		given:
		Template sidebarEpisodeTemplate = new Template(
				title: TemplateTitle.SIDEBAR_EPISODE,
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
		Page page = new Page(templates: Lists.newArrayList(sidebarEpisodeTemplate))
		Staff writer = Mock()
		Staff teleplayAuthor = Mock()
		Staff storyAuthor = Mock()
		Staff director = Mock()

		when:
		episodeStaffLinkingWorker.link(page, episode)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_EPISODE) >> Optional.of(sidebarEpisodeTemplate)

		then: 'gets writer from service'
		1 * wikitextApiMock.getPageLinksFromWikitext(WS_WRITTEN_BY_VALUE) >> Lists.newArrayList(new PageLink(title: WRITER_NAME))
		1 * entityLookupByNameServiceMock.findStaffByName(WRITER_NAME, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.of(writer)

		then: 'gets teleplay author from service'
		1 * wikitextApiMock.getPageLinksFromWikitext(WS_TELEPLAY_BY_VALUE) >> Lists.newArrayList(new PageLink(title: TELEPLAY_AUTHOR_NAME))
		1 * entityLookupByNameServiceMock.findStaffByName(TELEPLAY_AUTHOR_NAME, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.of(teleplayAuthor)

		then: 'gets story author from service'
		1 * wikitextApiMock.getPageLinksFromWikitext(WS_STORY_BY_VALUE) >> Lists.newArrayList(new PageLink(title: STORY_AUTHOR_NAME))
		1 * entityLookupByNameServiceMock.findStaffByName(STORY_AUTHOR_NAME, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.of(storyAuthor)

		then: 'gets director from service'
		1 * wikitextApiMock.getPageLinksFromWikitext(WS_DIRECTED_BY_VALUE) >> Lists.newArrayList(new PageLink(title: DIRECTOR_NAME))
		1 * entityLookupByNameServiceMock.findStaffByName(DIRECTOR_NAME, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.of(director)

		then: 'episode has staff set'
		episode.writers.contains writer
		episode.teleplayAuthors.contains teleplayAuthor
		episode.storyAuthors.contains storyAuthor
		episode.directors.contains director

		then: 'no other interactions are expected'
		0 * _
	}

	void "tolerates template part with null key"() {
		given:
		Template sidebarEpisodeTemplate = new Template(
				title: TemplateTitle.SIDEBAR_EPISODE,
				parts: Lists.newArrayList(
						new Template.Part(
								key: null,
								value: null
						)
				)
		)
		Episode episode = new Episode()
		Page page = new Page(templates: Lists.newArrayList(sidebarEpisodeTemplate))

		when:
		episodeStaffLinkingWorker.link(page, episode)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_EPISODE) >> Optional.of(sidebarEpisodeTemplate)
		notThrown(Exception)
	}

}
