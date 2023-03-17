package com.cezarykluczynski.stapi.etl.template.movie.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.template.movie.dto.MovieTemplate;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MovieTemplateProcessor implements ItemProcessor<Template, MovieTemplate> {

	private final MovieTemplateStaffEnrichingProcessor movieTemplateStaffEnrichingProcessor;

	public MovieTemplateProcessor(MovieTemplateStaffEnrichingProcessor movieTemplateStaffEnrichingProcessor) {
		this.movieTemplateStaffEnrichingProcessor = movieTemplateStaffEnrichingProcessor;
	}

	@Override
	@NonNull
	public MovieTemplate process(Template item) throws Exception {
		MovieTemplate movieTemplate = new MovieTemplate();
		Movie movieStub = new Movie();
		movieTemplate.setMovieStub(movieStub);

		movieTemplateStaffEnrichingProcessor.enrich(EnrichablePair.of(item, movieTemplate));

		return movieTemplate;
	}

}
