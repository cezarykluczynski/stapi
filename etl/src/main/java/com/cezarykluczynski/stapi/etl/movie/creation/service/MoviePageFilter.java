package com.cezarykluczynski.stapi.etl.movie.creation.service;

import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.util.constant.PageTitle;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MoviePageFilter implements MediaWikiPageFilter {

	private static final List<String> INVALID_TITLES = Lists.newArrayList(PageTitle.STAR_TREK_FILMS, PageTitle.STAR_TREK_XIV);

	@Override
	public boolean shouldBeFilteredOut(Page page) {
		return INVALID_TITLES.contains(page.getTitle());
	}

}
