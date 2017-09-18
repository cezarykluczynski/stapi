package com.cezarykluczynski.stapi.etl.title.creation.service;

import com.cezarykluczynski.stapi.etl.title.creation.processor.TitleListPageProcessor;
import com.cezarykluczynski.stapi.etl.title.creation.processor.TitleWriter;
import com.cezarykluczynski.stapi.model.title.entity.Title;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class TitleListCache {

	private final Set<Page> cache = Sets.newLinkedHashSet();

	private final TitleListPageProcessor titleListPageProcessor;

	private final TitleWriter titleWriter;

	@Inject
	public TitleListCache(TitleListPageProcessor titleListPageProcessor, TitleWriter titleWriter) {
		this.titleListPageProcessor = titleListPageProcessor;
		this.titleWriter = titleWriter;
	}

	public synchronized void add(Page page) {
		cache.add(page);
	}

	synchronized void produceTitlesFromListPages() {
		List<Title> titleList = Lists.newArrayList();

		cache.stream()
				.map(titleListPageProcessor::process)
				.forEach(titleList::addAll);

		try {
			titleWriter.write(titleList);
			cache.clear();
		} catch (Exception e) {
			log.error("Could not write titles from list pages", e);
		}
	}

}
