package com.cezarykluczynski.stapi.etl.common.service;

import com.cezarykluczynski.stapi.etl.common.mapper.MediaWikiSourceMapper;
import com.cezarykluczynski.stapi.model.common.repository.PageAwareRepository;
import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Service;

import javax.persistence.NonUniqueResultException;
import java.util.Optional;

@Service
class GenericEntityLookupByNameService {

	private final PageApi pageApi;

	private final MediaWikiSourceMapper mediaWikiSourceMapper;

	private final Repositories repositories;

	GenericEntityLookupByNameService(PageApi pageApi, MediaWikiSourceMapper mediaWikiSourceMapper, Repositories repositories) {
		this.pageApi = pageApi;
		this.mediaWikiSourceMapper = mediaWikiSourceMapper;
		this.repositories = repositories;
	}

	public <T> Optional<T> findEntityByName(String pageName, MediaWikiSource mediaWikiSource, Class<T> clazz) {
		Object repository = repositories.getRepositoryFor(clazz);
		if (!(repository instanceof PageAwareRepository)) {
			return Optional.empty();
		}

		@SuppressWarnings("unchecked")
		PageAwareRepository<T> pageAwareRepository = (PageAwareRepository<T>) repository;

		Optional<T> genericOptional;

		try {
			genericOptional = pageAwareRepository.findByPageTitleAndPageMediaWikiSource(pageName,
					mediaWikiSourceMapper.fromSourcesToEntity(mediaWikiSource));
		} catch (NonUniqueResultException e) {
			genericOptional = Optional.empty();
		}

		if (genericOptional.isPresent()) {
			return genericOptional;
		} else {
			Page page = pageApi.getPage(pageName, mediaWikiSource);
			if (page != null && !page.getRedirectPath().isEmpty()) {
				return pageAwareRepository.findByPagePageIdAndPageMediaWikiSource(page.getPageId(),
						mediaWikiSourceMapper.fromSourcesToEntity(page.getMediaWikiSource()));
			}
		}

		return Optional.empty();
	}

}
