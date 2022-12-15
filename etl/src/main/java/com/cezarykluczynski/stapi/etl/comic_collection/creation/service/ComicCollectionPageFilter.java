package com.cezarykluczynski.stapi.etl.comic_collection.creation.service;

import com.cezarykluczynski.stapi.etl.template.common.service.AbstractMediaWikiPageFilter;
import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilterConfiguration;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComicCollectionPageFilter extends AbstractMediaWikiPageFilter {

	private static final List<String> INVALID_TITLES = Lists.newArrayList(
			"Undeveloped Star Trek publications", // obvious
			"Star Trek: 50th Anniversary Cover Celebration", // just covers
			"Best of Spock", "Best of Klingons (Star Trek Archives)" // never released
	);

	public ComicCollectionPageFilter() {
		super(MediaWikiPageFilterConfiguration.builder()
				.invalidTitles(INVALID_TITLES)
				.build());
	}

}
