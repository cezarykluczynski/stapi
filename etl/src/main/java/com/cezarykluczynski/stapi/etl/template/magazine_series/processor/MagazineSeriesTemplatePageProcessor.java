package com.cezarykluczynski.stapi.etl.template.magazine_series.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.magazine.creation.service.MagazineCandidatePageGatheringService;
import com.cezarykluczynski.stapi.etl.magazine_series.creation.service.MagazineSeriesDetector;
import com.cezarykluczynski.stapi.etl.mediawiki.cache.PageCacheStorage;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.MonthYear;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.MonthYearRange;
import com.cezarykluczynski.stapi.etl.template.magazine_series.dto.MagazineSeriesTemplate;
import com.cezarykluczynski.stapi.etl.template.magazine_series.service.MagazineSeriesPageFilter;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.etl.util.TitleUtil;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MagazineSeriesTemplatePageProcessor implements ItemProcessor<Page, MagazineSeriesTemplate> {

	private static final String MAGAZINE = "(magazine";

	private final TemplateFinder templateFinder;

	private final MagazineSeriesDetector magazineSeriesDetector;

	private final MagazineCandidatePageGatheringService magazineCandidatePageGatheringService;

	private final PageCacheStorage pageCacheStorage;

	private final PageBindingService pageBindingService;

	private final MagazineSeriesTemplatePartsEnrichingProcessor magazineSeriesTemplatePartsEnrichingProcessor;

	private final MagazineSeriesPageFilter magazineSeriesPageFilter;

	private final MagazineSeriesPublicationDatesFixedValueProvider magazineSeriesPublicationDatesFixedValueProvider;

	private final MagazineSeriesNumberOfIssuesFixedValueProvider magazineSeriesNumberOfIssuesFixedValueProvider;

	@Override
	public MagazineSeriesTemplate process(Page item) throws Exception {
		if (magazineSeriesPageFilter.shouldBeFilteredOut(item)) {
			return null;
		}

		Optional<Template> sidebarMagazineSeriesTemplateOptional = templateFinder.findTemplate(item, TemplateTitle.SIDEBAR_MAGAZINE_SERIES);
		boolean sidebarMagazineSeriesTemplateIsPresent = sidebarMagazineSeriesTemplateOptional.isPresent();
		Optional<Template> sidebarMagazineTemplateOptional = templateFinder.findTemplate(item, TemplateTitle.SIDEBAR_MAGAZINE);
		boolean sidebarMagazineTemplateIsPresent = sidebarMagazineTemplateOptional.isPresent();
		Optional<Template> sidebarReferenceBookTemplateOptional = templateFinder.findTemplate(item, TemplateTitle.SIDEBAR_REFERENCE_BOOK);
		boolean sidebarReferenceBookTemplateIsPresent = sidebarReferenceBookTemplateOptional.isPresent();
		boolean isMagazineSeriesFromDetector = magazineSeriesDetector.isMagazineSeries(item);
		String title = item.getTitle();
		if (sidebarMagazineSeriesTemplateIsPresent || isMagazineSeriesFromDetector) {
			if (sidebarReferenceBookTemplateIsPresent || sidebarMagazineTemplateIsPresent) {
				log.error("Inconsistent magazine template findings for page \"{}\".", title);
			}
			// continue
		} else if (sidebarReferenceBookTemplateIsPresent || sidebarMagazineTemplateIsPresent) {
			pageCacheStorage.put(item);
			magazineCandidatePageGatheringService.addCandidate(item);
			return null;
		} else {
			log.info("No template {} found in magazine series page candidate \"{}\", and no entry in detector, skipping.",
					TemplateTitle.SIDEBAR_MAGAZINE, title);
			return null;
		}

		MagazineSeriesTemplate magazineSeriesTemplate = new MagazineSeriesTemplate();
		magazineSeriesTemplate.setTitle(StringUtils.containsAny(title, MAGAZINE) ? TitleUtil.getNameFromTitle(title) : title);
		magazineSeriesTemplate.setPage(pageBindingService.fromPageToPageEntity(item));

		if (sidebarMagazineSeriesTemplateIsPresent) {
			magazineSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(sidebarMagazineSeriesTemplateOptional.get().getParts(),
					magazineSeriesTemplate));
		}

		maybeAddPublicationDate(magazineSeriesTemplate);
		maybeAddNumberOfIssues(magazineSeriesTemplate);

		return magazineSeriesTemplate;
	}

	private void maybeAddPublicationDate(MagazineSeriesTemplate magazineSeriesTemplate) {
		FixedValueHolder<MonthYearRange> publicationDatesFixedValueHolder = magazineSeriesPublicationDatesFixedValueProvider
				.getSearchedValue(magazineSeriesTemplate.getTitle());
		if (publicationDatesFixedValueHolder.isFound()) {
			MonthYearRange publicationDates = publicationDatesFixedValueHolder.getValue();
			MonthYear publicationDateFrom = publicationDates.getFrom();
			MonthYear publicationDateTo = publicationDates.getTo();
			magazineSeriesTemplate.setPublishedMonthFrom(publicationDateFrom.getMonth());
			magazineSeriesTemplate.setPublishedYearFrom(publicationDateFrom.getYear());
			magazineSeriesTemplate.setPublishedMonthTo(publicationDateTo.getMonth());
			magazineSeriesTemplate.setPublishedYearTo(publicationDateTo.getYear());
		}
	}

	private void maybeAddNumberOfIssues(MagazineSeriesTemplate magazineSeriesTemplate) {
		FixedValueHolder<Integer> numberOfIssuesFixedValueHolder = magazineSeriesNumberOfIssuesFixedValueProvider
				.getSearchedValue(magazineSeriesTemplate.getTitle());

		if (numberOfIssuesFixedValueHolder.isFound()) {
			magazineSeriesTemplate.setNumberOfIssues(numberOfIssuesFixedValueHolder.getValue());
		}
	}

}
