package com.cezarykluczynski.stapi.etl.movie.creation.service;

import com.cezarykluczynski.stapi.etl.template.common.service.AbstractMediaWikiPageFilter;
import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilterConfiguration;
import com.cezarykluczynski.stapi.util.constant.PageTitle;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoviePageFilter extends AbstractMediaWikiPageFilter {

	private static final List<String> INVALID_TITLES = Lists.newArrayList(PageTitle.STAR_TREK_FILMS, PageTitle.STAR_TREK_XIV);

	public MoviePageFilter() {
		super(MediaWikiPageFilterConfiguration.builder()
				.invalidTitles(INVALID_TITLES)
				.build());
	}

}
