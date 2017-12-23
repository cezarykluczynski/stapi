package com.cezarykluczynski.stapi.etl.season.creation.service;

import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class SeasonPageFilter implements MediaWikiPageFilter {

	private static final String AFTER_TREK_PREFIX = "After Trek";

	@Override
	public boolean shouldBeFilteredOut(Page page) {
		return StringUtils.startsWith(page.getTitle(), AFTER_TREK_PREFIX);
	}

}
