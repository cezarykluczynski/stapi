package com.cezarykluczynski.stapi.etl.comic_strip.creation.processor;

import com.cezarykluczynski.stapi.etl.comic_strip.creation.service.ComicStripCandidatePageGatheringService;
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.etl.util.constant.JobName;
import com.cezarykluczynski.stapi.etl.util.constant.StepName;
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
@Slf4j
public class ComicStripReader implements ItemReader<PageHeader> {

	private final ComicStripCandidatePageGatheringService comicStripCandidatePageGatheringService;

	private final CategoryApi categoryApi;

	private final StepCompletenessDecider stepCompletenessDecider;

	private List<PageHeader> pageHeaderList;

	private Iterator<PageHeader> pageHeaderIterator;

	private boolean initialized;

	public ComicStripReader(ComicStripCandidatePageGatheringService comicStripCandidatePageGatheringService, CategoryApi categoryApi,
			StepCompletenessDecider stepCompletenessDecider) {
		this.comicStripCandidatePageGatheringService = comicStripCandidatePageGatheringService;
		this.categoryApi = categoryApi;
		this.stepCompletenessDecider = stepCompletenessDecider;
	}

	@Override
	public synchronized PageHeader read() throws Exception {
		if (!initialized) {
			initializeSourceList();
			log.info("Initial size of comic strips list: {}", pageHeaderList.size());
			createIterator();
			initialized = true;
		}

		return doRead();
	}

	private PageHeader doRead() {
		return pageHeaderIterator.hasNext() ? pageHeaderIterator.next() : null;
	}

	private void createIterator() {
		pageHeaderIterator = pageHeaderList.iterator();
	}

	private void initializeSourceList() {
		if (stepCompletenessDecider.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_COMIC_STRIPS)) {
			pageHeaderList = Lists.newArrayList();
		} else if (!comicStripCandidatePageGatheringService.isEmpty()) {
			pageHeaderList = comicStripCandidatePageGatheringService.getAllPageHeadersThenClean();
		} else {
			initializeFromApi();
		}
	}

	private void initializeFromApi() {
		List<PageHeader> comicStripsList = Lists.newArrayList();

		comicStripsList.addAll(categoryApi.getPages(CategoryTitle.COMICS, MediaWikiSource.MEMORY_ALPHA_EN));
		comicStripsList.addAll(categoryApi.getPages(CategoryTitle.PHOTONOVELS, MediaWikiSource.MEMORY_ALPHA_EN));

		pageHeaderList = Lists.newArrayList(Sets.newHashSet(comicStripsList));
	}

}
