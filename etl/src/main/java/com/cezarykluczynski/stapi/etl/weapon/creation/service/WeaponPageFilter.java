package com.cezarykluczynski.stapi.etl.weapon.creation.service;

import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService;
import com.cezarykluczynski.stapi.etl.template.common.service.AbstractMediaWikiPageFilter;
import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilterConfiguration;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.google.common.collect.Lists;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeaponPageFilter extends AbstractMediaWikiPageFilter {

	private static final List<String> INVALID_TITLES = Lists.newArrayList("Powder horn", "Kill setting");
	private static final List<String> INVALID_CATEGORIES_TO_BE_SORTED_ON_TOP_OF = Lists.newArrayList(CategoryTitle.WEAPONS);

	@Getter
	private final CategorySortingService categorySortingService;

	public WeaponPageFilter(CategorySortingService categorySortingService) {
		super(MediaWikiPageFilterConfiguration.builder()
				.skipRedirects(true)
				.invalidTitles(INVALID_TITLES)
				.invalidCategoriesToBeSortedOnTopOf(INVALID_CATEGORIES_TO_BE_SORTED_ON_TOP_OF)
				.build());
		this.categorySortingService = categorySortingService;
	}

}
