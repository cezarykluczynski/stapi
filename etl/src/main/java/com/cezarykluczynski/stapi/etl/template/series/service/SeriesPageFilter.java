package com.cezarykluczynski.stapi.etl.template.series.service;

import com.cezarykluczynski.stapi.etl.template.common.service.AbstractMediaWikiPageFilter;
import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilterConfiguration;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeriesPageFilter extends AbstractMediaWikiPageFilter {

	private static final List<String> INVALID_TITLES = Lists.newArrayList("Untitled Section 31 series");

	public SeriesPageFilter() {
		super(MediaWikiPageFilterConfiguration.builder()
				.skipRedirects(true)
				.invalidTitles(INVALID_TITLES)
				.build());
	}

}
