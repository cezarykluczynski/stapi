package com.cezarykluczynski.stapi.model.common.repository;

import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource;

import java.util.Optional;

public interface PageAwareRepository<T> {

	Optional<T> findByPageTitleAndPageMediaWikiSource(String title, MediaWikiSource mediaWikiSource);

	Optional<T> findByPagePageIdAndPageMediaWikiSource(Long pageId, MediaWikiSource mediaWikiSource);

}
