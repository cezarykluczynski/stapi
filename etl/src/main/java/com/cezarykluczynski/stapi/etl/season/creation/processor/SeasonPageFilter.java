package com.cezarykluczynski.stapi.etl.season.creation.processor;

import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeasonPageFilter implements MediaWikiPageFilter {

	private static final List<String> INVALID_TITLES = List.of("VST Season 1");

	@Override
	public boolean shouldBeFilteredOut(Page page) {
		return INVALID_TITLES.contains(page.getTitle());
	}
}
