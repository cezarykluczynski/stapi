package com.cezarykluczynski.stapi.etl.common.service;

import com.cezarykluczynski.stapi.etl.common.mapper.MediaWikiSourceMapper;
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.model.page.repository.PageRepository;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PageBindingService {

	private final PageRepository pageRepository;

	private final MediaWikiSourceMapper mediaWikiSourceMapper;

	public PageBindingService(PageRepository pageRepository, MediaWikiSourceMapper mediaWikiSourceMapper) {
		this.pageRepository = pageRepository;
		this.mediaWikiSourceMapper = mediaWikiSourceMapper;
	}

	public synchronized com.cezarykluczynski.stapi.model.page.entity.Page fromPageToPageEntity(Page page) {
		Optional<com.cezarykluczynski.stapi.model.page.entity.Page> pageEntityOptional = findPageByPageId(page.getPageId());

		if (pageEntityOptional.isPresent()) {
			return pageEntityOptional.get();
		}

		com.cezarykluczynski.stapi.model.page.entity.Page pageEntity = new com.cezarykluczynski.stapi.model.page.entity.Page();
		pageEntity.setPageId(page.getPageId());
		pageEntity.setTitle(page.getTitle());
		pageEntity.setMediaWikiSource(map(page.getMediaWikiSource()));
		return pageRepository.save(pageEntity);
	}

	public synchronized com.cezarykluczynski.stapi.model.page.entity.Page fromPageHeaderToPageEntity(PageHeader pageHeader) {
		Optional<com.cezarykluczynski.stapi.model.page.entity.Page> pageEntityOptional = findPageByPageId(pageHeader.getPageId());

		if (pageEntityOptional.isPresent()) {
			return pageEntityOptional.get();
		}

		com.cezarykluczynski.stapi.model.page.entity.Page pageEntity = new com.cezarykluczynski.stapi.model.page.entity.Page();
		pageEntity.setPageId(pageHeader.getPageId());
		pageEntity.setTitle(pageHeader.getTitle());
		pageEntity.setMediaWikiSource(map(pageHeader.getMediaWikiSource()));
		return pageRepository.save(pageEntity);
	}

	private Optional<com.cezarykluczynski.stapi.model.page.entity.Page> findPageByPageId(Long pageId) {
		return pageRepository.findByPageId(pageId);
	}

	public MediaWikiSource map(com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource mediaWikiSource) {
		return mediaWikiSourceMapper.fromSourcesToEntity(mediaWikiSource);
	}



}
