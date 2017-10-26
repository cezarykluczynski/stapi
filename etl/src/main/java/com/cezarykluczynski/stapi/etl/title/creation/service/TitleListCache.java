package com.cezarykluczynski.stapi.etl.title.creation.service;

import com.cezarykluczynski.stapi.etl.title.creation.processor.list.TitleListPageProcessor;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class TitleListCache {

	private final Set<Page> cache = Sets.newLinkedHashSet();

	private final TitleListPageProcessor titleListPageProcessor;

	public TitleListCache(TitleListPageProcessor titleListPageProcessor) {
		this.titleListPageProcessor = titleListPageProcessor;
	}

	public synchronized void add(Page page) {
		cache.add(page);
	}

	@Transactional
	public synchronized void produceTitlesFromListPages() {
		cache.forEach(titleListPageProcessor::process);
		cache.clear();
	}

}
