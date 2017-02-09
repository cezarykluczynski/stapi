package com.cezarykluczynski.stapi.etl.template.comicSeries.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.template.comicSeries.dto.ComicSeriesTemplate;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.PageName;
import com.cezarykluczynski.stapi.util.constant.TemplateName;
import com.google.common.collect.Sets;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Optional;
import java.util.Set;

@Service
public class ComicSeriesTemplatePageProcessor implements ItemProcessor<Page, ComicSeriesTemplate> {

	private static final Set<String> INVALID_TITLES = Sets.newHashSet(PageName.DC_COMICS_TNG_TIMELINE, PageName.STAR_TREK_PROBABILITY_FACTOR,
			PageName.STAR_TREK_THE_ORIGINAL_SERIES_DC, PageName.STAR_TREK_ANNUAL, PageName.STAR_TREK_VOYAGER_MALIBU);

	private PageBindingService pageBindingService;

	private TemplateFinder templateFinder;

	private ComicSeriesTemplatePartsEnrichingProcessor comicSeriesTemplatePartsEnrichingProcessor;

	private ComicSeriesTemplateFixedValuesEnrichingProcessor comicSeriesTemplateFixedValuesEnrichingProcessor;

	private ComicSeriesTemplatePhotonovelSeriesProcessor comicSeriesTemplatePhotonovelSeriesProcessor;

	@Inject
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
		comicSeriesTemplate.setTitle(item.getTitle());
		comicSeriesTemplate.setPage(pageBindingService.fromPageToPageEntity(item));
		comicSeriesTemplate.setProductOfRedirect(!item.getRedirectPath().isEmpty());
		comicSeriesTemplate.setPhotonovelSeries(comicSeriesTemplatePhotonovelSeriesProcessor.process(item));

		comicSeriesTemplateFixedValuesEnrichingProcessor.enrich(EnrichablePair.of(comicSeriesTemplate, comicSeriesTemplate));

		Optional<Template> sidebarIndividualTemplateOptional = templateFinder.findTemplate(item, TemplateName.SIDEBAR_COMIC_SERIES);
		if (!sidebarIndividualTemplateOptional.isPresent()) {
			return comicSeriesTemplate;
		}

		comicSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(sidebarIndividualTemplateOptional.get().getParts(), comicSeriesTemplate));

		return comicSeriesTemplate;
	}

	private boolean shouldBeFilteredOut(Page item) {
		return INVALID_TITLES.contains(item.getTitle());
	}
}
