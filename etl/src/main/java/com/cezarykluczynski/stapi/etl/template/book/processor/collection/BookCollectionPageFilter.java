package com.cezarykluczynski.stapi.etl.template.book.processor.collection;

import com.cezarykluczynski.stapi.etl.template.common.service.AbstractMediaWikiPageFilter;
import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilterConfiguration;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookCollectionPageFilter extends AbstractMediaWikiPageFilter {

	private static final List<String> INVALID_TITLES = Lists.newArrayList(
			"Star Trek - Convention Special", // sampler
			"Star Trek: Crucible" // cancelled
	);

	public BookCollectionPageFilter() {
		super(MediaWikiPageFilterConfiguration.builder()
				.invalidTitles(INVALID_TITLES)
				.build());
	}

}
