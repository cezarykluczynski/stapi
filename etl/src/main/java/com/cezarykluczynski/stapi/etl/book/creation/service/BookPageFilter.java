package com.cezarykluczynski.stapi.etl.book.creation.service;

import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService;
import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookPageFilter implements MediaWikiPageFilter {

	private static final Set<String> INVALID_TITLES = Sets.newHashSet("Schuster & Schuster", "Star Trek Roleplaying Game (Last Unicorn)",
			"Starlog photo guidebook Special Effects", "The Best of Star Trek (Titan)", "Star Trek: Federation Gift Pak",
			"To Boldly Go: Rare Photos from the TOS Soundstage", "These Are the Voyages: TOS", "Star Trek Emissary Gift Set",
			"Captain Sulu Adventures", "The Best of Trek", "Stardate (magazine)");

	private final CategorySortingService categorySortingService;

	private final CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	@Override
	public boolean shouldBeFilteredOut(Page page) {
		List<String> categoryTitleList = categoryTitlesExtractingProcessor.process(page.getCategories());
		if (categoryTitleList.stream().anyMatch(categoryTitle -> StringUtils.startsWithIgnoreCase(categoryTitle, "comic"))) {
			log.info("Filtering out book {} because of comics-related categories: {}.", page.getTitle(), categoryTitleList);
			return true;
		}

		return !page.getRedirectPath().isEmpty() || categorySortingService.isSortedOnTopOfAnyCategory(page)
				|| INVALID_TITLES.contains(page.getTitle());
	}

}
