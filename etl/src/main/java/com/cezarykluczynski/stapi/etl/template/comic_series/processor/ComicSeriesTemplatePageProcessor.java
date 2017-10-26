package com.cezarykluczynski.stapi.etl.template.comic_series.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.template.comic_series.dto.ComicSeriesTemplate;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.etl.util.TitleUtil;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.PageTitle;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import com.google.common.collect.Sets;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ComicSeriesTemplatePageProcessor implements ItemProcessor<Page, ComicSeriesTemplate> {

	private static final Set<String> INVALID_TITLES = Sets.newHashSet(PageTitle.DC_COMICS_TNG_TIMELINE, PageTitle.STAR_TREK_PROBABILITY_FACTOR,
			PageTitle.STAR_TREK_THE_ORIGINAL_SERIES_DC, PageTitle.STAR_TREK_ANNUAL, PageTitle.STAR_TREK_VOYAGER_MALIBU);

	private static final Set<String> TITLE_PARTS_REQUIRING_CLEANING = Sets.newHashSet("(comic)", "(photonovel)");

	private final PageBindingService pageBindingService;

	private final TemplateFinder templateFinder;

	private final ComicSeriesTemplatePartsEnrichingProcessor comicSeriesTemplatePartsEnrichingProcessor;

	private final ComicSeriesTemplateFixedValuesEnrichingProcessor comicSeriesTemplateFixedValuesEnrichingProcessor;

	private final ComicSeriesTemplatePhotonovelSeriesProcessor comicSeriesTemplatePhotonovelSeriesProcessor;

	public ComicSeriesTemplatePageProcessor(PageBindingService pageBindingService, TemplateFinder templateFinder,
			ComicSeriesTemplatePartsEnrichingProcessor comicSeriesTemplatePartsEnrichingProcessor,
			ComicSeriesTemplateFixedValuesEnrichingProcessor comicSeriesTemplateFixedValuesEnrichingProcessor,
			ComicSeriesTemplatePhotonovelSeriesProcessor comicSeriesTemplatePhotonovelSeriesProcessor) {
		this.pageBindingService = pageBindingService;
		this.templateFinder = templateFinder;
		this.comicSeriesTemplatePartsEnrichingProcessor = comicSeriesTemplatePartsEnrichingProcessor;
		this.comicSeriesTemplateFixedValuesEnrichingProcessor = comicSeriesTemplateFixedValuesEnrichingProcessor;
		this.comicSeriesTemplatePhotonovelSeriesProcessor = comicSeriesTemplatePhotonovelSeriesProcessor;
	}

	@Override
	public ComicSeriesTemplate process(Page item) throws Exception {
		if (shouldBeFilteredOut(item)) {
			return null;
		}

		ComicSeriesTemplate comicSeriesTemplate = new ComicSeriesTemplate();
		comicSeriesTemplate.setTitle(getTitle(item.getTitle()));
		comicSeriesTemplate.setPage(pageBindingService.fromPageToPageEntity(item));
		comicSeriesTemplate.setPhotonovelSeries(comicSeriesTemplatePhotonovelSeriesProcessor.process(item));

		comicSeriesTemplateFixedValuesEnrichingProcessor.enrich(EnrichablePair.of(comicSeriesTemplate, comicSeriesTemplate));

		Optional<Template> sidebarComicSeriesTemplateOptional = templateFinder.findTemplate(item, TemplateTitle.SIDEBAR_COMIC_SERIES);
		if (!sidebarComicSeriesTemplateOptional.isPresent()) {
			return comicSeriesTemplate;
		}

		comicSeriesTemplatePartsEnrichingProcessor
				.enrich(EnrichablePair.of(sidebarComicSeriesTemplateOptional.get().getParts(), comicSeriesTemplate));

		return comicSeriesTemplate;
	}

	private String getTitle(String title) {
		return TITLE_PARTS_REQUIRING_CLEANING.stream().anyMatch(title::contains) ? TitleUtil.getNameFromTitle(title) : title;
	}

	private boolean shouldBeFilteredOut(Page item) {
		return INVALID_TITLES.contains(item.getTitle()) || !item.getRedirectPath().isEmpty();
	}

}
