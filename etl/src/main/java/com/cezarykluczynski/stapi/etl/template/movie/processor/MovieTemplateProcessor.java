package com.cezarykluczynski.stapi.etl.template.movie.processor;

import com.cezarykluczynski.stapi.etl.common.configuration.CommonTemplateConfiguration;
import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ImageTemplateStardateYearEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.movie.dto.MovieTemplate;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@Slf4j

public class MovieTemplateProcessor implements ItemProcessor<Template, MovieTemplate> {

	private final ImageTemplateStardateYearEnrichingProcessor imageTemplateStardateYearEnrichingProcessor;

	private final MovieTemplateStaffEnrichingProcessor movieTemplateStaffEnrichingProcessor;

	public MovieTemplateProcessor(@Qualifier(CommonTemplateConfiguration.MOVIE_TEMPALTE_STARDATE_YEAR_ENRICHING_PROCESSOR)
					ImageTemplateStardateYearEnrichingProcessor imageTemplateStardateYearEnrichingProcessor,
			MovieTemplateStaffEnrichingProcessor movieTemplateStaffEnrichingProcessor) {
		this.imageTemplateStardateYearEnrichingProcessor = imageTemplateStardateYearEnrichingProcessor;
		this.movieTemplateStaffEnrichingProcessor = movieTemplateStaffEnrichingProcessor;
	}

	@Override
	@NonNull
	public MovieTemplate process(Template item) throws Exception {
		MovieTemplate movieTemplate = new MovieTemplate();
		Movie movieStub = new Movie();
		movieTemplate.setMovieStub(movieStub);

		imageTemplateStardateYearEnrichingProcessor.enrich(EnrichablePair.of(item, movieTemplate));
		movieTemplateStaffEnrichingProcessor.enrich(EnrichablePair.of(item, movieTemplate));

		return movieTemplate;
	}

}
