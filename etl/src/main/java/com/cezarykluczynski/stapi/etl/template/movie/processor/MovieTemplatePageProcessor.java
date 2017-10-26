package com.cezarykluczynski.stapi.etl.template.movie.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.template.movie.dto.MovieTemplate;
import com.cezarykluczynski.stapi.etl.template.movie.linker.MovieRealPeopleLinkingWorkerComposite;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.etl.util.TitleUtil;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.PageTitle;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import com.google.common.collect.Lists;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieTemplatePageProcessor implements ItemProcessor<Page, MovieTemplate> {

	private static final List<String> IGNORABLE_TITLES = Lists.newArrayList(PageTitle.STAR_TREK_FILMS,
			PageTitle.STAR_TREK_XIV);

	private final MovieTemplateProcessor movieTemplateProcessor;

	private final TemplateFinder templateFinder;

	private final PageBindingService pageBindingService;

	private final MovieTemplateTitleLanguagesEnrichingProcessor movieTemplateTitleLanguagesEnrichingProcessor;

	private final MovieRealPeopleLinkingWorkerComposite movieRealPeopleLinkingWorkerComposite;

	public MovieTemplatePageProcessor(MovieTemplateProcessor movieTemplateProcessor, TemplateFinder templateFinder,
			PageBindingService pageBindingService,
			MovieTemplateTitleLanguagesEnrichingProcessor movieTemplateTitleLanguagesEnrichingProcessor,
			MovieRealPeopleLinkingWorkerComposite movieRealPeopleLinkingWorkerComposite) {
		this.movieTemplateProcessor = movieTemplateProcessor;
		this.templateFinder = templateFinder;
		this.pageBindingService = pageBindingService;
		this.movieTemplateTitleLanguagesEnrichingProcessor = movieTemplateTitleLanguagesEnrichingProcessor;
		this.movieRealPeopleLinkingWorkerComposite = movieRealPeopleLinkingWorkerComposite;
	}

	@Override
	public MovieTemplate process(Page item) throws Exception {
		Optional<Template> templateOptional = templateFinder.findTemplate(item, TemplateTitle.SIDEBAR_FILM);

		if (!isMoviePage(item) || !templateOptional.isPresent()) {
			return null;
		}

		MovieTemplate movieTemplate = movieTemplateProcessor.process(templateOptional.get());
		movieTemplate.setTitle(TitleUtil.getNameFromTitle(item.getTitle()));
		movieTemplate.setPage(pageBindingService.fromPageToPageEntity(item));

		movieTemplateTitleLanguagesEnrichingProcessor.enrich(EnrichablePair.of(item, movieTemplate));
		movieRealPeopleLinkingWorkerComposite.link(item, movieTemplate.getMovieStub());

		return movieTemplate;
	}

	private boolean isMoviePage(Page source) {
		return !IGNORABLE_TITLES.contains(source.getTitle());
	}

}
