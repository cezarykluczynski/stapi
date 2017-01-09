package com.cezarykluczynski.stapi.etl.template.movie.processor;

import com.cezarykluczynski.stapi.etl.common.configuration.CommonTemplateConfiguration;
import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ImageTemplateStardateYearEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.common.dto.DayMonthYearCandidate;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.DayMonthYearProcessor;
import com.cezarykluczynski.stapi.etl.template.movie.dto.MovieTemplate;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
@Slf4j

public class MovieTemplateProcessor implements ItemProcessor<Template, MovieTemplate> {

	private static final String N_RELEASE_YEAR = "nreleaseyear";
	private static final String S_RELEASE_MONTH = "sreleasemonth";
	private static final String N_RELEASE_DAY = "nreleaseday";

	private DayMonthYearProcessor dayMonthYearProcessor;

	private ImageTemplateStardateYearEnrichingProcessor imageTemplateStardateYearEnrichingProcessor;

	private MovieTemplateStaffEnrichingProcessor movieTemplateStaffEnrichingProcessor;

	@Inject
	public MovieTemplateProcessor(DayMonthYearProcessor dayMonthYearProcessor,
			@Qualifier(CommonTemplateConfiguration.MOVIE_TEMPALTE_STARDATE_YEAR_ENRICHING_PROCESSOR)
					ImageTemplateStardateYearEnrichingProcessor imageTemplateStardateYearEnrichingProcessor,
			MovieTemplateStaffEnrichingProcessor movieTemplateStaffEnrichingProcessor) {
		this.dayMonthYearProcessor = dayMonthYearProcessor;
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
				case N_RELEASE_YEAR:
					year = value;
					break;
				case S_RELEASE_MONTH:
					month = value;
					break;
				case N_RELEASE_DAY:
					day = value;
					break;
				default:
					break;
			}
		}

		if (day != null && month != null && year != null) {
			movieTemplate.setUsReleaseDate(dayMonthYearProcessor.process(DayMonthYearCandidate
					.of(day, month, year)));
		}

		return movieTemplate;
	}

}
