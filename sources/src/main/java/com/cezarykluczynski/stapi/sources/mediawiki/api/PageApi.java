package com.cezarykluczynski.stapi.sources.mediawiki.api;

import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import info.bliki.api.PageInfo;

import java.util.List;

public interface PageApi {

	Page getPage(String title, MediaWikiSource mediaWikiSource);

	default Page getTemplate(String title, MediaWikiSource mediaWikiSource) {
		return getPage("Template:" + title, mediaWikiSource);
	}

	default Page getCategory(String title, MediaWikiSource mediaWikiSource) {
		return getPage("Category:" + title, mediaWikiSource);
	}

	PageInfo getPageInfo(String title, MediaWikiSource mediaWikiSource);

	List<Page> getPages(List<String> titles, MediaWikiSource mediaWikiSource);

}
