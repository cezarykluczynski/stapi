package com.cezarykluczynski.stapi.etl.template.movie.processor;

import com.cezarykluczynski.stapi.etl.common.configuration.CommonTemplateConfiguration;
import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ImageTemplateStardateYearEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYearCandidate;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.DayMonthYearCandidateToLocalDateProcessor;
import com.cezarykluczynski.stapi.etl.template.movie.dto.MovieTemplate;
import com.cezarykluczynski.stapi.etl.template.movie.dto.MovieTemplateParameter;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Slf4j

public class MovieTemplateProcessor implements ItemProcessor<Template, MovieTemplate> {

	private final DayMonthYearCandidateToLocalDateProcessor dayMonthYearCandidateToLocalDateProcessor;

	private final ImageTemplateStardateYearEnrichingProcessor imageTemplateStardateYearEnrichingProcessor;

	private final MovieTemplateStaffEnrichingProcessor movieTemplateStaffEnrichingProcessor;

	public MovieTemplateProcessor(DayMonthYearCandidateToLocalDateProcessor dayMonthYearCandidateToLocalDateProcessor,
			@Qualifier(CommonTemplateConfiguration.MOVIE_TEMPALTE_STARDATE_YEAR_ENRICHING_PROCESSOR)
					ImageTemplateStardateYearEnrichingProcessor imageTemplateStardateYearEnrichingProcessor,
			MovieTemplateStaffEnrichingProcessor movieTemplateStaffEnrichingProcessor) {
		this.dayMonthYearCandidateToLocalDateProcessor = dayMonthYearCandidateToLocalDateProcessor;
		this.imageTemplateStardateYearEnrichingProcessor = imageTemplateStardateYearEnrichingProcessor;
		this.movieTemplateStaffEnrichingProcessor = movieTemplateStaffEnrichingProcessor;
	}

	@Override
	public MovieTemplate process(Template item) throws Exception {
		MovieTemplate movieTemplate = new MovieTemplate();
		Movie movieStub = new Movie();
		movieTemplate.setMovieStub(movieStub);

		imageTemplateStardateYearEnrichingProcessor.enrich(EnrichablePair.of(item, movieTemplate));
		movieTemplateStaffEnrichingProcessor.enrich(EnrichablePair.of(item, movieTemplate));

		String day = null;
		String month = null;
		String year = null;

		for (Template.Part part : item.getParts()) {
			String key = part.getKey();
			String value = part.getValue();

			switch (key) {
				case MovieTemplateParameter.N_RELEASE_YEAR:
					year = value;
					break;
				case MovieTemplateParameter.S_RELEASE_MONTH:
					month = value;
					break;
				case MovieTemplateParameter.N_RELEASE_DAY:
					day = value;
					break;
				default:
					break;
			}
		}

		if (day != null && month != null && year != null) {
			movieTemplate.setUsReleaseDate(dayMonthYearCandidateToLocalDateProcessor.process(DayMonthYearCandidate
					.of(day, month, year)));
		}

		return movieTemplate;
	}

}
