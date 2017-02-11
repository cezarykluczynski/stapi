package com.cezarykluczynski.stapi.etl.comicStrip.creation.service;

import com.cezarykluczynski.stapi.sources.mediawiki.converter.PageHeaderConverter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class ComicStripCandidatePageGatheringService {

	private final List<Page> pageList = Lists.newArrayList();

	private PageHeaderConverter pageHeaderConverter;

	@Inject
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
