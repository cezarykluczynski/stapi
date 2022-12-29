package com.cezarykluczynski.stapi.model.common.repository;

import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource;
import liquibase.util.StringUtil;

import java.util.Optional;

public interface PageAwareRepository<T> {

	default Optional<T> findByPageTitleWithPageMediaWikiSource(String title, MediaWikiSource mediaWikiSource) {
		// often links from wikitext are passed here, and those sometimes has first letter lowercase, and falsely return no results
		return findByPageTitleAndPageMediaWikiSource(StringUtil.upperCaseFirst(title), mediaWikiSource);
	}

	Optional<T> findByPageTitleAndPageMediaWikiSource(String title, MediaWikiSource mediaWikiSource);

	Optional<T> findByPagePageIdAndPageMediaWikiSource(Long pageId, MediaWikiSource mediaWikiSource);

}
