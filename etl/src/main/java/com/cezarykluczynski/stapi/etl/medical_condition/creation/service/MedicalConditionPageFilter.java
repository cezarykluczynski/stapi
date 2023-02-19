package com.cezarykluczynski.stapi.etl.medical_condition.creation.service;

import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService;
import com.cezarykluczynski.stapi.etl.template.common.service.AbstractMediaWikiPageFilter;
import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilterConfiguration;
import com.google.common.collect.Lists;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalConditionPageFilter extends AbstractMediaWikiPageFilter {

	private static final List<String> INVALID_TITLES = Lists.newArrayList("Health", "Carrier");

	@Getter
	private final CategorySortingService categorySortingService;

	public MedicalConditionPageFilter(CategorySortingService categorySortingService) {
		super(MediaWikiPageFilterConfiguration.builder()
				.skipRedirects(true)
				.invalidTitles(INVALID_TITLES)
				.skipPagesSortedOnTopOfAnyCategory(true)
				.build());
		this.categorySortingService = categorySortingService;
	}

}
