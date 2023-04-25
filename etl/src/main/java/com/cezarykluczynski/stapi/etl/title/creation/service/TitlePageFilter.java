package com.cezarykluczynski.stapi.etl.title.creation.service;

import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.etl.template.common.service.MediaWikiPageFilter;
import com.cezarykluczynski.stapi.util.constant.PageTitle;
import com.google.common.collect.Sets;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TitlePageFilter implements MediaWikiPageFilter {

	private static final String RANKS = " ranks";
	private static final Set<String> TITLES_TO_FILTER_OUT = Sets.newHashSet(PageTitle.STARFLEET_RANKS, PageTitle.FERENGI_RANKS,
			PageTitle.SCHUTZSTAFFEL_UNIFORMS_AND_INSIGNIA, PageTitle.NATIONAL_SOCIALIST_RANKS);

	private final CategorySortingService categorySortingService;

	private final TitleListCache titleListCache;

	@SuppressFBWarnings("EI_EXPOSE_REP2")
	public TitlePageFilter(CategorySortingService categorySortingService, TitleListCache titleListCache) {
		this.categorySortingService = categorySortingService;
		this.titleListCache = titleListCache;
	}

	@Override
	public boolean shouldBeFilteredOut(Page page) {
		if (TITLES_TO_FILTER_OUT.contains(page.getTitle())) {
			return true;
		}

		if (!page.getRedirectPath().isEmpty()) {
			return true;
		}

		if (categorySortingService.isSortedOnTopOfAnyCategory(page)) {
			if (StringUtils.contains(page.getTitle(), RANKS)) {
				titleListCache.add(page);
			}

			return true;
		}

		return false;
	}

}
