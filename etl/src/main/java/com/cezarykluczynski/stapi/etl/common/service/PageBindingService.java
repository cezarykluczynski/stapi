package com.cezarykluczynski.stapi.etl.common.service;

import com.cezarykluczynski.stapi.etl.common.mapper.MediaWikiSourceMapper;
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.model.page.repository.PageRepository;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Optional;

@Service
public class PageBindingService {

	private PageRepository pageRepository;

	private MediaWikiSourceMapper mediaWikiSourceMapper;

	@Inject
	public PageBindingService(PageRepository pageRepository, MediaWikiSourceMapper mediaWikiSourceMapper) {
		this.pageRepository = pageRepository;
		this.mediaWikiSourceMapper = mediaWikiSourceMapper;
	}

	public com.cezarykluczynski.stapi.model.page.entity.Page fromPageToPageEntity(Page page) {
		Optional<com.cezarykluczynski.stapi.model.page.entity.Page> pageEntityOptional
				= findPageByPageId(page.getPageId());

		if (pageEntityOptional.isPresent()) {
			return pageEntityOptional.get();
		}

		return pageRepository.save(com.cezarykluczynski.stapi.model.page.entity.Page.builder()
				.pageId(page.getPageId())
				.title(page.getTitle())
				.mediaWikiSource(map(page.getMediaWikiSource()))
				.build());
	}

	public com.cezarykluczynski.stapi.model.page.entity.Page fromPageHeaderToPageEntity(PageHeader pageHeader) {
		Optional<com.cezarykluczynski.stapi.model.page.entity.Page> pageEntityOptional
				= findPageByPageId(pageHeader.getPageId());

		if (pageEntityOptional.isPresent()) {
			return pageEntityOptional.get();
		}

		return pageRepository.save(com.cezarykluczynski.stapi.model.page.entity.Page.builder()
				.pageId(pageHeader.getPageId())
				.title(pageHeader.getTitle())
				.mediaWikiSource(map(pageHeader.getMediaWikiSource()))
				.build());
	}

	private Optional<com.cezarykluczynski.stapi.model.page.entity.Page> findPageByPageId(Long pageId) {
		return pageRepository.findByPageId(pageId);
	}

	public MediaWikiSource map(com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource mediaWikiSource) {
		return mediaWikiSourceMapper.fromSourcesToEntity(mediaWikiSource);
	}



}
