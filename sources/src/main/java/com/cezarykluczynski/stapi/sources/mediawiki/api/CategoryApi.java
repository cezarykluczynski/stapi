package com.cezarykluczynski.stapi.sources.mediawiki.api;

import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;

import java.util.List;
import java.util.regex.Pattern;

public interface CategoryApi {

	List<PageHeader> getPages(String title, MediaWikiSource mediaWikiSource);

	List<PageHeader> getPages(List<String> titleList, MediaWikiSource mediaWikiSource);

	List<PageHeader> getPagesIncludingSubcategories(String title, MediaWikiSource mediaWikiSource);

	List<PageHeader> getPagesIncludingSubcategories(List<String> titleList, MediaWikiSource mediaWikiSource);

	List<PageHeader> getPagesIncludingSubcategoriesExcept(String title, List<String> exceptions, MediaWikiSource mediaWikiSource);

	List<PageHeader> getPagesIncludingSubcategoriesExcluding(String title, List<Pattern> exceptions, MediaWikiSource mediaWikiSource);

}
