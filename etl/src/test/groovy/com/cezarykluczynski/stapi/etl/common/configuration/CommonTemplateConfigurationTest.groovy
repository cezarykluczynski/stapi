package com.cezarykluczynski.stapi.etl.common.configuration

import com.cezarykluczynski.stapi.etl.common.processor.ImageTemplateStardateYearEnrichingProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.StardateYearProcessor
import com.cezarykluczynski.stapi.etl.template.episode.processor.EpisodeTemplateStardateYearFixedValueProvider
import com.cezarykluczynski.stapi.etl.template.movie.processor.MovieTemplateStardateYearFixedValueProvider
import org.springframework.context.ApplicationContext
import spock.lang.Specification

class CommonTemplateConfigurationTest extends Specification {

	private ApplicationContext applicationContextMock

	private CommonTemplateConfiguration commonTemplateConfiguration

	def setup() {
		applicationContextMock = Mock(ApplicationContext)
		commonTemplateConfiguration = new CommonTemplateConfiguration(applicationContext: applicationContextMock)
	}

	def "creates EPISODE_TEMPALTE_STARDATE_YEAR_ENRICHING_PROCESSOR"() {
		given:
		EpisodeTemplateStardateYearFixedValueProvider episodeTemplateStardateYearFixedValueProvider =
				Mock(EpisodeTemplateStardateYearFixedValueProvider)
		StardateYearProcessor stardateYearProcessor = Mock(StardateYearProcessor)

		when:
		ImageTemplateStardateYearEnrichingProcessor episodeTemplateStardateYearEnrichingProcessor =
				commonTemplateConfiguration.episodeTemplateStardateYearEnrichingProcessor()

		then:
		1 * applicationContextMock.getBean(EpisodeTemplateStardateYearFixedValueProvider) >> episodeTemplateStardateYearFixedValueProvider
		1 * applicationContextMock.getBean(StardateYearProcessor) >> stardateYearProcessor
		episodeTemplateStardateYearEnrichingProcessor.stardateYearDTOFixedValueProvider instanceof EpisodeTemplateStardateYearFixedValueProvider
		episodeTemplateStardateYearEnrichingProcessor.stardateYearProcessor instanceof StardateYearProcessor
	}

	def "creates MOVIE_TEMPALTE_STARDATE_YEAR_ENRICHING_PROCESSOR"() {
		given:
		MovieTemplateStardateYearFixedValueProvider movieTemplateStardateYearFixedValueProvider =
				Mock(MovieTemplateStardateYearFixedValueProvider)
		StardateYearProcessor stardateYearProcessor = Mock(StardateYearProcessor)

		when:
		ImageTemplateStardateYearEnrichingProcessor episodeTemplateStardateYearEnrichingProcessor =
				commonTemplateConfiguration.movieTemplateStardateYearEnrichingProcessor()

		then:
		1 * applicationContextMock.getBean(MovieTemplateStardateYearFixedValueProvider) >> movieTemplateStardateYearFixedValueProvider
		1 * applicationContextMock.getBean(StardateYearProcessor) >> stardateYearProcessor
		episodeTemplateStardateYearEnrichingProcessor.stardateYearDTOFixedValueProvider instanceof MovieTemplateStardateYearFixedValueProvider
		episodeTemplateStardateYearEnrichingProcessor.stardateYearProcessor instanceof StardateYearProcessor
	}

}
