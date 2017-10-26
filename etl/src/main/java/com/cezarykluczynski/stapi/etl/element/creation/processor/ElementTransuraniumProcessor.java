package com.cezarykluczynski.stapi.etl.element.creation.processor;

import com.cezarykluczynski.stapi.etl.common.service.PageSectionExtractor;
import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageSection;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.util.constant.PageTitle;
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException;
import com.cezarykluczynski.stapi.util.tool.StringUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElementTransuraniumProcessor {

	private static final String LIST_OF_TRANSURANIC_ELEMENTS = "List of transuranic elements";

	private final PageApi pageApi;

	private final WikitextApi wikitextApi;

	private final PageSectionExtractor pageSectionExtractor;

	private List<String> pageTitleList;

	public ElementTransuraniumProcessor(PageApi pageApi, WikitextApi wikitextApi, PageSectionExtractor pageSectionExtractor) {
		this.pageApi = pageApi;
		this.wikitextApi = wikitextApi;
		this.pageSectionExtractor = pageSectionExtractor;
	}

	public boolean isTransuranium(String title) {
		loadTransuranicPageTitleList();
		return StringUtil.containsIgnoreCase(pageTitleList, title);
	}

	private void loadTransuranicPageTitleList() {
		synchronized (this) {
			if (pageTitleList == null) {
				Page page = pageApi.getPage(PageTitle.TRANSURANIC, MediaWikiSource.MEMORY_ALPHA_EN);
				if (page == null) {
					throw new StapiRuntimeException(String.format("Cannot get page %s", PageTitle.TRANSURANIC));
				}

				List<PageSection> pageSectionList = pageSectionExtractor.findByTitles(page, LIST_OF_TRANSURANIC_ELEMENTS);
				if (pageSectionList.size() != 1) {
					throw new StapiRuntimeException(String.format("Cannot get page section %s", LIST_OF_TRANSURANIC_ELEMENTS));
				}

				pageTitleList = wikitextApi.getPageTitlesFromWikitext(pageSectionList.get(0).getWikitext());
			}
		}

	}
}
