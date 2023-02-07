package com.cezarykluczynski.stapi.etl.video_release.creation.service;

import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService;
import com.cezarykluczynski.stapi.etl.template.common.service.AbstractMediaWikiPageFilter;
import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilterConfiguration;
import com.google.common.collect.Sets;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class VideoReleasePageFilter extends AbstractMediaWikiPageFilter {

	private static final Set<String> INVALID_TITLES = Sets.newHashSet("Star Trek: Fan Collective");
	private static final Set<String> INVALID_PREFIXES = Sets.newHashSet("Star Trek films (");

	@Getter
	private final CategorySortingService categorySortingService;

	public VideoReleasePageFilter(CategorySortingService categorySortingService) {
		super(MediaWikiPageFilterConfiguration.builder()
				.skipRedirects(true)
				.invalidTitles(INVALID_TITLES)
				.invalidPrefixes(INVALID_PREFIXES)
				.skipPagesSortedOnTopOfAnyCategory(true)
				.build());
		this.categorySortingService = categorySortingService;
	}

}
