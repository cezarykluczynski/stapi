package com.cezarykluczynski.stapi.etl.template.individual.service;

import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.template.common.service.PageFilter;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitles;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class IndividualTemplateFilter implements PageFilter {

	private static final String UNNAMED_PREFIX = "Unnamed";
	private static final String LIST_OF_PREFIX = "List of ";
	private static final String MEMORY_ALPHA_IMAGES_PREFIX = "Memory Alpha images";
	private static final String PERSONNEL = "personnel";

	private static final Set<String> NOT_CHARACTERS_CATEGORY_TITLES = Sets.newHashSet();

	static {
		NOT_CHARACTERS_CATEGORY_TITLES.addAll(CategoryTitles.LISTS);
		NOT_CHARACTERS_CATEGORY_TITLES.add(CategoryTitle.FAMILIES);
	}

	private CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	private WikitextApi wikitextApi;

	public IndividualTemplateFilter(CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor, WikitextApi wikitextApi) {
		this.categoryTitlesExtractingProcessor = categoryTitlesExtractingProcessor;
		this.wikitextApi = wikitextApi;
	}

	@Override
	public boolean shouldBeFilteredOut(Page page) {
		String title = page.getTitle();
		if (StringUtils.startsWithAny(title, UNNAMED_PREFIX, LIST_OF_PREFIX, MEMORY_ALPHA_IMAGES_PREFIX) || page.getTitle().contains(PERSONNEL)) {
			return true;
		}

		List<String> categoryTitles = categoryTitlesExtractingProcessor.process(page.getCategories());

		if (categoryTitles.stream().anyMatch(NOT_CHARACTERS_CATEGORY_TITLES::contains)) {
			return true;
		}

		if (categoryTitles.stream().anyMatch(categoryTitle -> categoryTitle.startsWith(UNNAMED_PREFIX))) {
			return true;
		}

		List<PageLink> pageLinkList = wikitextApi.getPageLinksFromWikitext(page.getWikitext());

		List<PageLink> categoryPageLinkList = pageLinkList
				.stream()
				.filter(pageLink -> pageLink.getTitle().toLowerCase().startsWith("category:"))
				.filter(pageLink -> pageLink.getDescription() != null && pageLink.getDescription().length() == 0)
				.collect(Collectors.toList());

		if (!categoryPageLinkList.isEmpty()) {
			return true;
		}

		return false;
	}

}
