package com.cezarykluczynski.stapi.etl.spacecraft_class.creation.service;

import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService;
import com.cezarykluczynski.stapi.etl.template.common.service.AbstractMediaWikiPageFilter;
import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilterConfiguration;
import com.google.common.collect.Lists;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpacecraftClassPageFilter extends AbstractMediaWikiPageFilter {

	private static final List<String> INVALID_PREFIXES = Lists.newArrayList("Unnamed");

	@Getter
	private final CategorySortingService categorySortingService;

	public SpacecraftClassPageFilter(CategorySortingService categorySortingService) {
		super(MediaWikiPageFilterConfiguration.builder()
				.skipRedirects(true)
				.invalidPrefixes(INVALID_PREFIXES)
				.skipPagesSortedOnTopOfAnyCategory(true)
				.build());
		this.categorySortingService = categorySortingService;
	}

}
