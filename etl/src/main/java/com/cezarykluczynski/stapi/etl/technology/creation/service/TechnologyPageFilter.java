package com.cezarykluczynski.stapi.etl.technology.creation.service;

import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService;
import com.cezarykluczynski.stapi.etl.template.common.service.AbstractMediaWikiPageFilter;
import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilterConfiguration;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitles;
import lombok.Getter;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechnologyPageFilter extends AbstractMediaWikiPageFilter {

	private static final List<String> INVALID_TITLES = Lists.newArrayList("Canteen", "Computer expert", "Device", "Diagnosis", "Electronics",
			"Fan mail", "Identification", "Instruction", "Machine", "Optronics", "Root", "Series", "Temporal arms race", "Utensil");

	@Getter
	private final CategorySortingService categorySortingService;

	public TechnologyPageFilter(CategorySortingService categorySortingService) {
		super(MediaWikiPageFilterConfiguration.builder()
				.skipRedirects(true)
				.invalidTitles(INVALID_TITLES)
				.invalidCategoriesToBeSortedOnTopOf(CategoryTitles.TECHNOLOGY)
				.build());
		this.categorySortingService = categorySortingService;
	}

}
