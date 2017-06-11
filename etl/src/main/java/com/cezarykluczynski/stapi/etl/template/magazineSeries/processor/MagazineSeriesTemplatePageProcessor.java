package com.cezarykluczynski.stapi.etl.template.magazineSeries.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.magazine.creation.service.MagazineCandidatePageGatheringService;
import com.cezarykluczynski.stapi.etl.template.magazineSeries.dto.MagazineSeriesTemplate;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.etl.util.TitleUtil;
import com.cezarykluczynski.stapi.sources.mediawiki.cache.PageCacheStorage;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.PageTitle;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Optional;
import java.util.Set;

@Service
public class MagazineSeriesTemplatePageProcessor implements ItemProcessor<Page, MagazineSeriesTemplate> {

	private static final Set<String> INVALID_TITLES = Sets.newHashSet(PageTitle.MAGAZINES, PageTitle.PARTWORK);
	private static final String MAGAZINE = "(magazine";
	private static final String COMIC_MAGAZINE = "(comic magazine";

	private final TemplateFinder templateFinder;

	private final MagazineCandidatePageGatheringService magazineCandidatePageGatheringService;

	private final PageCacheStorage pageCacheStorage;

	private final PageBindingService pageBindingService;

	private final MagazineSeriesTemplatePartsEnrichingProcessor magazineSeriesTemplatePartsEnrichingProcessor;

	@Inject
	public MagazineSeriesTemplatePageProcessor(TemplateFinder templateFinder,
			MagazineCandidatePageGatheringService magazineCandidatePageGatheringService,
			PageCacheStorage pageCacheStorage, PageBindingService pageBindingService,
			MagazineSeriesTemplatePartsEnrichingProcessor magazineSeriesTemplatePartsEnrichingProcessor) {
		this.templateFinder = templateFinder;
		this.magazineCandidatePageGatheringService = magazineCandidatePageGatheringService;
		this.pageCacheStorage = pageCacheStorage;
		this.pageBindingService = pageBindingService;
		this.magazineSeriesTemplatePartsEnrichingProcessor = magazineSeriesTemplatePartsEnrichingProcessor;
	}

	@Override
	public MagazineSeriesTemplate process(Page item) throws Exception {
		if (shouldBeFilteredOut(item)) {
			return null;
		}

		Optional<Template> sidebarMagazineTemplateOptional = templateFinder.findTemplate(item, TemplateTitle.SIDEBAR_MAGAZINE);

		if (sidebarMagazineTemplateOptional.isPresent()) {
			pageCacheStorage.put(item);
			magazineCandidatePageGatheringService.addCandidate(item);
			return null;
		}

		String title = item.getTitle();
		MagazineSeriesTemplate magazineSeriesTemplate = new MagazineSeriesTemplate();
		magazineSeriesTemplate.setTitle(StringUtils.containsAny(title, MAGAZINE, COMIC_MAGAZINE) ? TitleUtil.getNameFromTitle(title) : title);
		magazineSeriesTemplate.setPage(pageBindingService.fromPageToPageEntity(item));

		Optional<Template> sidebarMagazineSeriesTemplateOptional = templateFinder.findTemplate(item, TemplateTitle.SIDEBAR_MAGAZINE_SERIES);
		if (!sidebarMagazineSeriesTemplateOptional.isPresent()) {
			return null;
		}

		magazineSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(sidebarMagazineSeriesTemplateOptional.get().getParts(),
				magazineSeriesTemplate));

		return magazineSeriesTemplate;
	}

	private boolean shouldBeFilteredOut(Page item) {
		return INVALID_TITLES.contains(item.getTitle()) || !item.getRedirectPath().isEmpty();
	}

}
