package com.cezarykluczynski.stapi.etl.template.series.service;

import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class SeriesPageFilter implements MediaWikiPageFilter {

	private static final String AFTER_TREK = "After Trek";

	@Override
	public boolean shouldBeFilteredOut(Page page) {
		return StringUtils.equals(page.getTitle(), AFTER_TREK);
	}

}
