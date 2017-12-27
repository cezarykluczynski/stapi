package com.cezarykluczynski.stapi.etl.magazine.creation.service;

import com.cezarykluczynski.stapi.etl.magazine_series.creation.service.MagazineSeriesDetector;
import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilter;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.util.constant.PageTitle;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MagazinePageFilter implements MediaWikiPageFilter {

	private static final Set<String> INVALID_TITLES = Sets.newHashSet(PageTitle.MAGAZINES, PageTitle.PARTWORK, "The Case of Jonathan Doe Starship",
			"Star Trek Magazine - The Archives", "Star Trek: The Motion Picture (comic magazine)", "Starfleet Technical Database");

	private final MagazineSeriesDetector magazineSeriesDetector;

	private final TemplateFinder templateFinder;

	public MagazinePageFilter(MagazineSeriesDetector magazineSeriesDetector, TemplateFinder templateFinder) {
		this.magazineSeriesDetector = magazineSeriesDetector;
		this.templateFinder = templateFinder;
	}

	@Override
	public boolean shouldBeFilteredOut(Page item) {
		return INVALID_TITLES.contains(item.getTitle()) || !item.getRedirectPath().isEmpty() || magazineSeriesDetector.isMagazineSeries(item)
				|| templateFinder.findTemplate(item, TemplateTitle.SIDEBAR_MAGAZINE_SERIES).isPresent();
	}

}
