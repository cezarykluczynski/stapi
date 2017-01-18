package com.cezarykluczynski.stapi.etl.template.episode.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.episode.dto.EpisodeTemplate
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import spock.lang.Specification

class EpisodeTemplateEnrichingProcessorCompositeTest extends Specification {

	private EpisodeTemplateDatesEnrichingProcessor episodeTemplateDatesEnrichingProcessorMock

	private EpisodeTemplateTitleLanguagesEnrichingProcessor episodeTemplateTitleLanguagesEnrichingProcessorMock

	private EpisodeTemplateEnrichingProcessorComposite episodeTemplateEnrichingProcessorComposite

	def setup() {
		episodeTemplateDatesEnrichingProcessorMock = Mock(EpisodeTemplateDatesEnrichingProcessor)
		episodeTemplateTitleLanguagesEnrichingProcessorMock = Mock(EpisodeTemplateTitleLanguagesEnrichingProcessor)
		episodeTemplateEnrichingProcessorComposite = new EpisodeTemplateEnrichingProcessorComposite(episodeTemplateDatesEnrichingProcessorMock,
				episodeTemplateTitleLanguagesEnrichingProcessorMock)
	}

	def "passes argument to dependencies"() {
		given:
		EnrichablePair<Page, EpisodeTemplate> enrichablePair = EnrichablePair.of(Mock(Page), Mock(EpisodeTemplate))

		when:
		episodeTemplateEnrichingProcessorComposite.enrich(enrichablePair)

		then:
		1 * episodeTemplateDatesEnrichingProcessorMock.enrich(enrichablePair)
		1 * episodeTemplateTitleLanguagesEnrichingProcessorMock.enrich(enrichablePair)
		0 * _
	}

}
