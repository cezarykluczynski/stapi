package com.cezarykluczynski.stapi.etl.episode.creation.processor

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor
import com.cezarykluczynski.stapi.etl.template.episode.dto.EpisodeTemplate
import com.cezarykluczynski.stapi.etl.template.episode.processor.ToEpisodeTemplateProcessor
import com.cezarykluczynski.stapi.model.episode.entity.Episode
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import spock.lang.Specification

class EpisodeProcessorTest extends Specification {

	private PageHeaderProcessor pageHeaderProcessorMock

	private ToEpisodeTemplateProcessor toEpisodeTemplateProcessorMock

	private ToEpisodeEntityProcessor toEpisodeEntityProcessorMock

	private EpisodeProcessor episodeProcessor

	void setup() {
		pageHeaderProcessorMock = Mock()
		toEpisodeTemplateProcessorMock = Mock()
		toEpisodeEntityProcessorMock = Mock()
		episodeProcessor = new EpisodeProcessor(pageHeaderProcessorMock, toEpisodeTemplateProcessorMock,
				toEpisodeEntityProcessorMock)
	}

	void "converts PageHeader to Episode"() {
		given:
		PageHeader pageHeader = new PageHeader()
		Page page = new Page()
		EpisodeTemplate episodeTemplate = new EpisodeTemplate()
		Episode episode = new Episode()

		when:
		Episode episodeOutput = episodeProcessor.process(pageHeader)

		then: 'processors are used in right order'
		1 * pageHeaderProcessorMock.process(pageHeader) >> page

		and:
		1 * toEpisodeTemplateProcessorMock.process(page) >> episodeTemplate

		and:
		1 * toEpisodeEntityProcessorMock.process(episodeTemplate) >> episode

		then: 'last processor output is returned'
		episodeOutput == episode
	}

}
