package com.cezarykluczynski.stapi.etl.common.configuration;

import com.cezarykluczynski.stapi.etl.common.processor.ImageTemplateStardateYearEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.StardateYearProcessor;
import com.cezarykluczynski.stapi.etl.template.episode.processor.EpisodeTemplateStardateYearFixedValueProvider;
import com.cezarykluczynski.stapi.etl.template.movie.processor.MovieTemplateStardateYearFixedValueProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;

@Configuration
public class CommonTemplateConfiguration {

	public static final String EPISODE_TEMPALTE_STARDATE_YEAR_ENRICHING_PROCESSOR = "EPISODE_TEMPALTE_STARDATE_YEAR_ENRICHING_PROCESSOR";
	public static final String MOVIE_TEMPALTE_STARDATE_YEAR_ENRICHING_PROCESSOR = "MOVIE_TEMPALTE_STARDATE_YEAR_ENRICHING_PROCESSOR";

	@Inject
	private ApplicationContext applicationContext;

	@Bean(EPISODE_TEMPALTE_STARDATE_YEAR_ENRICHING_PROCESSOR)
	public ImageTemplateStardateYearEnrichingProcessor episodeTemplateStardateYearEnrichingProcessor() {
		return new ImageTemplateStardateYearEnrichingProcessor(
				applicationContext.getBean(EpisodeTemplateStardateYearFixedValueProvider.class),
				applicationContext.getBean(StardateYearProcessor.class));
	}

	@Bean(MOVIE_TEMPALTE_STARDATE_YEAR_ENRICHING_PROCESSOR)
	public ImageTemplateStardateYearEnrichingProcessor movieTemplateStardateYearEnrichingProcessor() {
		return new ImageTemplateStardateYearEnrichingProcessor(
				applicationContext.getBean(MovieTemplateStardateYearFixedValueProvider.class),
				applicationContext.getBean(StardateYearProcessor.class));
	}


}
