package com.cezarykluczynski.stapi.etl.template.magazine_series.service;

import com.cezarykluczynski.stapi.etl.template.common.service.AbstractMediaWikiPageFilter;
import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilterConfiguration;
import com.cezarykluczynski.stapi.util.constant.PageTitle;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MagazineSeriesPageFilter extends AbstractMediaWikiPageFilter {

	private static final Set<String> INVALID_TITLES = Sets.newHashSet(PageTitle.MAGAZINES, PageTitle.PARTWORK);

	public MagazineSeriesPageFilter() {
		super(MediaWikiPageFilterConfiguration.builder()
				.skipRedirects(true)
				.invalidTitles(INVALID_TITLES)
				.build());
	}

}
