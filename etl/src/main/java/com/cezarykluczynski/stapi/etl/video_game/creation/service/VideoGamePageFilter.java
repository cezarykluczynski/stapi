package com.cezarykluczynski.stapi.etl.video_game.creation.service;

import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class VideoGamePageFilter implements MediaWikiPageFilter {

	private static final Set<String> INVALID_TITLES = Sets.newHashSet("Star Trek: Starfleet Command series");

	@Override
	public boolean shouldBeFilteredOut(Page page) {
		return !page.getRedirectPath().isEmpty() || INVALID_TITLES.contains(page.getTitle());
	}

}
