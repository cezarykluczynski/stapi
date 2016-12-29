package com.cezarykluczynski.stapi.etl.template.movie.processor;

import com.cezarykluczynski.stapi.etl.common.configuration.CommonTemplateConfiguration;
import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ImageTemplateStardateYearEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.common.service.EntityLookupByNameService;
import com.cezarykluczynski.stapi.etl.template.common.dto.DayMonthYearCandidate;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.DayMonthYearProcessor;
import com.cezarykluczynski.stapi.etl.template.movie.dto.MovieTemplate;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j

public class MovieTemplateProcessor implements ItemProcessor<Template, MovieTemplate> {

	private static final MediaWikiSource SOURCE = MediaWikiSource.MEMORY_ALPHA_EN;

	private static final String N_RELEASE_YEAR = "nreleaseyear";
	private static final String S_RELEASE_MONTH = "sreleasemonth";
	private static final String N_RELEASE_DAY = "nreleaseday";
	private static final String WS_SCREENPLAY_BY = "wsscreenplayby";
	private static final String WS_STORY_BY = "wsstoryby";
	private static final String WS_DIRECTED_BY = "sdirectedby";
	private static final String WS_PRODUCED_BY = "wsproducedby";

	private DayMonthYearProcessor dayMonthYearProcessor;

	private ImageTemplateStardateYearEnrichingProcessor imageTemplateStardateYearEnrichingProcessor;

	private WikitextApi wikitextApi;

	private EntityLookupByNameService entityLookupByNameService;

	@Inject
	public MovieTemplateProcessor(DayMonthYearProcessor dayMonthYearProcessor,
			@Qualifier(CommonTemplateConfiguration.MOVIE_TEMPALTE_STARDATE_YEAR_ENRICHING_PROCESSOR)
					ImageTemplateStardateYearEnrichingProcessor imageTemplateStardateYearEnrichingProcessor,
			WikitextApi wikitextApi, EntityLookupByNameService entityLookupByNameService) {
		this.dayMonthYearProcessor = dayMonthYearProcessor;
		this.imageTemplateStardateYearEnrichingProcessor = imageTemplateStardateYearEnrichingProcessor;
		this.wikitextApi = wikitextApi;
		this.entityLookupByNameService = entityLookupByNameService;
	}

	@Override
	public MovieTemplate process(Template item) throws Exception {
		MovieTemplate movieTemplate = new MovieTemplate();
		Movie movieStub = new Movie();
		movieTemplate.setMovieStub(movieStub);

		imageTemplateStardateYearEnrichingProcessor.enrich(EnrichablePair.of(item, movieTemplate));

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
				case WS_SCREENPLAY_BY:
					movieStub.getScreenplayAuthors().addAll(findAllStaff(value));
					break;
				case WS_STORY_BY:
					movieStub.getStoryAuthors().addAll(findAllStaff(value));
					break;
				case WS_DIRECTED_BY:
					movieStub.getDirectors().addAll(findAllStaff(value));
					break;
				case WS_PRODUCED_BY:
					movieStub.getProducers().addAll(findAllStaff(value));
					break;
			}
		}

		if (day != null && month != null && year != null) {
			movieTemplate.setUsReleaseDate(dayMonthYearProcessor.process(DayMonthYearCandidate
					.of(day, month, year)));
		}

		return movieTemplate;
	}

	private Set<Staff> findAllStaff(String value) {
		List<String> linkTitleList = wikitextApi.getPageTitlesFromWikitext(value);

		return linkTitleList
				.stream()
				.map(linkTitle -> {
					Optional<Staff> staffOptional = entityLookupByNameService.findStaffByName(linkTitle, SOURCE);
					if (staffOptional.isPresent()) {
						log.info("Adding staff {}", staffOptional.get().getName());
						return staffOptional.get();
					}

					log.warn("Staff {} not found by name", linkTitle);
					return null;
				})
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());
	}
}
