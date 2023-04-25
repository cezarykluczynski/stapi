package com.cezarykluczynski.stapi.etl.template.movie.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.episode.creation.dto.ModuleEpisodeData;
import com.cezarykluczynski.stapi.etl.episode.creation.service.ModuleEpisodeDataProvider;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.etl.movie.creation.service.MoviePageFilter;
import com.cezarykluczynski.stapi.etl.template.movie.dto.MovieTemplate;
import com.cezarykluczynski.stapi.etl.template.movie.linker.MovieRealPeopleLinkingWorkerComposite;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.etl.util.TitleUtil;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieTemplatePageProcessor implements ItemProcessor<Page, MovieTemplate> {

	private final MoviePageFilter moviePageFilter;

	private final MovieTemplateProcessor movieTemplateProcessor;

	private final TemplateFinder templateFinder;

	private final PageBindingService pageBindingService;

	private final MovieTemplateTitleLanguagesEnrichingProcessor movieTemplateTitleLanguagesEnrichingProcessor;

	private final MovieRealPeopleLinkingWorkerComposite movieRealPeopleLinkingWorkerComposite;

	private final ModuleEpisodeDataProvider moduleEpisodeDataProvider;

	private final ModuleMovieDataEnrichingProcessor moduleMovieDataEnrichingProcessor;

	private final MovieDateEnrichingProcessor movieDateEnrichingProcessor;

	@Override
	public MovieTemplate process(Page item) throws Exception {
		if (moviePageFilter.shouldBeFilteredOut(item)) {
			return null;
		}
		String title = item.getTitle();
		Optional<Template> templateOptional = templateFinder.findTemplate(item, TemplateTitle.SIDEBAR_FILM);
		if (!templateOptional.isPresent()) {
			log.info("Template \"{}\" not found in page \"{}\", skipping.", TemplateTitle.SIDEBAR_FILM, title);
			return null;
		}
		log.info("Processing movie \"{}\".", item.getTitle());

		MovieTemplate movieTemplate = movieTemplateProcessor.process(templateOptional.get());
		movieTemplate.setTitle(TitleUtil.getNameFromTitle(title));
		movieTemplate.setPage(pageBindingService.fromPageToPageEntity(item));

		final ModuleEpisodeData moduleEpisodeData = moduleEpisodeDataProvider.provideDataFor(title);
		if (moduleEpisodeData != null) {
			moduleMovieDataEnrichingProcessor.enrich(EnrichablePair.of(moduleEpisodeData, movieTemplate));
		} else {
			log.info("No ModuleEpisodeData found for \"{}\".", title);
		}

		movieTemplateTitleLanguagesEnrichingProcessor.enrich(EnrichablePair.of(item, movieTemplate));
		movieDateEnrichingProcessor.enrich(EnrichablePair.of(item, movieTemplate));
		movieRealPeopleLinkingWorkerComposite.link(item, movieTemplate.getMovieStub());

		return movieTemplate;
	}

}
