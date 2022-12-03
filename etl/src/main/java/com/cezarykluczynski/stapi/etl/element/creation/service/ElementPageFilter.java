package com.cezarykluczynski.stapi.etl.element.creation.service;

import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService;
import com.cezarykluczynski.stapi.etl.template.common.service.AbstractMediaWikiPageFilter;
import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilterConfiguration;
import lombok.Getter;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElementPageFilter extends AbstractMediaWikiPageFilter {

	private static final List<String> INVALID_TITLES = Lists.newArrayList("Lanthanide", "Sulfide", "Carbon-70", "Actinide", "Brass", "Illium-629",
			"Neutronium", "Noranium", "Periodic table", "Rodinium", "Thoron", "Trionium", "Verterium");

	@Getter
	private final CategorySortingService categorySortingService;

	public ElementPageFilter(CategorySortingService categorySortingService) {
		super(MediaWikiPageFilterConfiguration.builder()
				.skipRedirects(true)
				.invalidTitles(INVALID_TITLES)
				.skipPagesSortedOnTopOfAnyCategory(true)
				.build());
		this.categorySortingService = categorySortingService;
	}

}
