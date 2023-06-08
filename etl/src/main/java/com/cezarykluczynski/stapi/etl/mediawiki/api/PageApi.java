package com.cezarykluczynski.stapi.etl.mediawiki.api;

import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.PageHeader;
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

	List<PageHeader> getPageHeaders(List<String> titles, MediaWikiSource mediaWikiSource);

}
