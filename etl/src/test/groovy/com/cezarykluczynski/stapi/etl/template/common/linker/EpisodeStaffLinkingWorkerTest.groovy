package com.cezarykluczynski.stapi.etl.template.common.linker

import com.cezarykluczynski.stapi.etl.common.service.EntityLookupByNameService
import com.cezarykluczynski.stapi.etl.template.episode.dto.EpisodeTemplateParameter
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.model.episode.entity.Episode
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.etl.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.etl.mediawiki.api.dto.PageLink
import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.google.common.collect.Lists
import spock.lang.Specification

class EpisodeStaffLinkingWorkerTest extends Specification {

	private static final String WRITER_NAME = 'WRITER_NAME'
	private static final String TELEPLAY_AUTHOR_NAME = 'TELEPLAY_AUTHOR_NAME'
	private static final String STORY_AUTHOR_NAME = 'STORY_AUTHOR_NAME'
	private static final String DIRECTOR_NAME = 'DIRECTOR_NAME'
	private static final String WRITER_VALUE = 'WRITER_VALUE'
	private static final String TELEPLAY_VALUE = 'TELEPLAY_VALUE'
	private static final String STORY_VALUE = 'STORY_VALUE' + WRITER_NAME
	private static final String DIRECTOR_VALUE = 'DIRECTOR_VALUE' + TELEPLAY_AUTHOR_NAME

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
								key: EpisodeTemplateParameter.WRITER,
								value: WRITER_VALUE
						),
						new Template.Part(
								key: EpisodeTemplateParameter.TELEPLAY,
								value: TELEPLAY_VALUE
						),
						new Template.Part(
								key: EpisodeTemplateParameter.STORY,
								value: STORY_VALUE
						),
						new Template.Part(
								key: EpisodeTemplateParameter.DIRECTOR,
								value: DIRECTOR_VALUE
						)
				)
		)
		Episode episode = new Episode()
		Page page = new Page(templates: Lists.newArrayList(sidebarEpisodeTemplate))
		Staff writer = new Staff(name: WRITER_NAME)
		Staff teleplayAuthor = new Staff(name: TELEPLAY_AUTHOR_NAME)
		Staff storyAuthor = new Staff(name: STORY_AUTHOR_NAME)
		Staff director = new Staff(name: DIRECTOR_NAME)

		when:
		episodeStaffLinkingWorker.link(page, episode)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_EPISODE) >> Optional.of(sidebarEpisodeTemplate)

		then: 'gets writer from service'
		1 * wikitextApiMock.getPageLinksFromWikitext(WRITER_VALUE) >> Lists.newArrayList(new PageLink(title: WRITER_NAME))
		1 * entityLookupByNameServiceMock.findStaffByName(WRITER_NAME, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.of(writer)

		then: 'gets teleplay author from service'
		1 * wikitextApiMock.getPageLinksFromWikitext(TELEPLAY_VALUE) >> Lists.newArrayList(new PageLink(title: TELEPLAY_AUTHOR_NAME))
		1 * entityLookupByNameServiceMock.findStaffByName(TELEPLAY_AUTHOR_NAME, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.of(teleplayAuthor)

		then: 'gets story author from service'
		1 * wikitextApiMock.getPageLinksFromWikitext(STORY_VALUE) >> Lists.newArrayList(new PageLink(title: STORY_AUTHOR_NAME))
		1 * entityLookupByNameServiceMock.findStaffByName(STORY_AUTHOR_NAME, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.of(storyAuthor)

		then: 'gets director from service'
		1 * wikitextApiMock.getPageLinksFromWikitext(DIRECTOR_VALUE) >> Lists.newArrayList(new PageLink(title: DIRECTOR_NAME))
		1 * entityLookupByNameServiceMock.findStaffByName(DIRECTOR_NAME, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.of(director)

		then: 'episode has staff set'
		episode.writers.contains writer
		episode.teleplayAuthors.contains teleplayAuthor
		episode.storyAuthors.contains storyAuthor
		episode.storyAuthors.contains writer
		episode.directors.contains director
		episode.directors.contains teleplayAuthor

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
