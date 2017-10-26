package com.cezarykluczynski.stapi.etl.comic_strip.creation.service;

import com.cezarykluczynski.stapi.sources.mediawiki.converter.PageHeaderConverter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComicStripCandidatePageGatheringService {

	private final PageHeaderConverter pageHeaderConverter;

	private final List<Page> pageList = Lists.newArrayList();

	public ComicStripCandidatePageGatheringService(PageHeaderConverter pageHeaderConverter) {
		this.pageHeaderConverter = pageHeaderConverter;
	}

	public synchronized void addCandidate(Page page) {
		pageList.add(page);
	}

	public synchronized boolean isEmpty() {
		return pageList.isEmpty();
	}

	public synchronized List<PageHeader> getAllPageHeadersThenClean() {
		List<Page> pagesToReturn = Lists.newArrayList(pageList);
		pageList.clear();
		return pageHeaderConverter.fromPageList(pagesToReturn);
	}

}
