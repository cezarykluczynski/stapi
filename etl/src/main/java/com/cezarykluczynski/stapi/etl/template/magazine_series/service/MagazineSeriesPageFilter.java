package com.cezarykluczynski.stapi.etl.template.magazine_series.service;

import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.util.constant.PageTitle;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MagazineSeriesPageFilter implements MediaWikiPageFilter {

	private static final Set<String> INVALID_TITLES = Sets.newHashSet(PageTitle.MAGAZINES, PageTitle.PARTWORK);

	@Override
	public boolean shouldBeFilteredOut(Page page) {
		return INVALID_TITLES.contains(page.getTitle()) || !page.getRedirectPath().isEmpty();
	}

}
