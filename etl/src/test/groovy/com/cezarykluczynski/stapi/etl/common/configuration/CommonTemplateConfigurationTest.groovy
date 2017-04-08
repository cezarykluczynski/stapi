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

	void setup() {
		applicationContextMock = Mock()
		commonTemplateConfiguration = new CommonTemplateConfiguration(applicationContext: applicationContextMock)
	}

	void "creates EPISODE_TEMPALTE_STARDATE_YEAR_ENRICHING_PROCESSOR"() {
		given:
		EpisodeTemplateStardateYearFixedValueProvider episodeTemplateStardateYearFixedValueProvider = Mock()
		StardateYearProcessor stardateYearProcessor = Mock()

		when:
		ImageTemplateStardateYearEnrichingProcessor episodeTemplateStardateYearEnrichingProcessor =
				commonTemplateConfiguration.episodeTemplateStardateYearEnrichingProcessor()

		then:
		1 * applicationContextMock.getBean(EpisodeTemplateStardateYearFixedValueProvider) >> episodeTemplateStardateYearFixedValueProvider
		1 * applicationContextMock.getBean(StardateYearProcessor) >> stardateYearProcessor
		episodeTemplateStardateYearEnrichingProcessor.stardateYearDTOFixedValueProvider instanceof EpisodeTemplateStardateYearFixedValueProvider
		episodeTemplateStardateYearEnrichingProcessor.stardateYearProcessor instanceof StardateYearProcessor
	}

	void "creates MOVIE_TEMPALTE_STARDATE_YEAR_ENRICHING_PROCESSOR"() {
		given:
		MovieTemplateStardateYearFixedValueProvider movieTemplateStardateYearFixedValueProvider = Mock()
		StardateYearProcessor stardateYearProcessor = Mock()

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
