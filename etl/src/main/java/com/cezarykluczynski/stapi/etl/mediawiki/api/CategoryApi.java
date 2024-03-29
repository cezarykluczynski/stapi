package com.cezarykluczynski.stapi.etl.mediawiki.api;

import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.CategoryHeader;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.PageHeader;

import java.util.List;
import java.util.regex.Pattern;

public interface CategoryApi {

	List<PageHeader> getPages(String title, MediaWikiSource mediaWikiSource);

	List<PageHeader> getPages(List<String> titleList, MediaWikiSource mediaWikiSource);

	List<PageHeader> getPagesIncludingSubcategories(String title, MediaWikiSource mediaWikiSource);

	List<PageHeader> getPagesIncludingSubcategories(String title, int depth, MediaWikiSource mediaWikiSource);

	List<PageHeader> getPagesIncludingSubcategories(List<String> titleList, MediaWikiSource mediaWikiSource);

	List<PageHeader> getPagesIncludingSubcategories(List<String> titleList, int depth, MediaWikiSource mediaWikiSource);

	List<PageHeader> getPagesIncludingSubcategoriesExcept(String title, List<String> exceptions, MediaWikiSource mediaWikiSource);

	List<PageHeader> getPagesIncludingSubcategoriesExcluding(String title, List<Pattern> exceptions, int depth, MediaWikiSource mediaWikiSource);

	List<CategoryHeader> getCategoriesInCategory(String categoryTitle, MediaWikiSource mediaWikiSource);

	List<CategoryHeader> getCategoriesInCategoryIncludingSubcategories(String categoryTitle, MediaWikiSource mediaWikiSource,
		List<String> excluding);

}
