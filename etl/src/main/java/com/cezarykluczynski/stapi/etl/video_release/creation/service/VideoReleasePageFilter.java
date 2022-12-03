package com.cezarykluczynski.stapi.etl.video_release.creation.service;

import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService;
import com.cezarykluczynski.stapi.etl.template.common.service.AbstractMediaWikiPageFilter;
import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilterConfiguration;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
public class VideoReleasePageFilter extends AbstractMediaWikiPageFilter {

	@Getter
	private final CategorySortingService categorySortingService;

	public VideoReleasePageFilter(CategorySortingService categorySortingService) {
		super(MediaWikiPageFilterConfiguration.builder()
				.skipRedirects(true)
				.skipPagesSortedOnTopOfAnyCategory(true)
				.build());
		this.categorySortingService = categorySortingService;
	}

}
