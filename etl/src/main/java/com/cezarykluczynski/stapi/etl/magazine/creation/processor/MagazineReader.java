package com.cezarykluczynski.stapi.etl.magazine.creation.processor;

import com.cezarykluczynski.stapi.etl.common.processor.SizeAwareItemReader;
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider;
import com.cezarykluczynski.stapi.etl.magazine.creation.service.MagazineCandidatePageGatheringService;
import com.cezarykluczynski.stapi.etl.mediawiki.api.CategoryApi;
import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.PageHeader;
import com.cezarykluczynski.stapi.etl.util.SortingUtil;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.etl.util.constant.JobName;
import com.cezarykluczynski.stapi.etl.util.constant.StepName;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
@Slf4j
public class MagazineReader implements ItemReader<PageHeader>, SizeAwareItemReader {

	private final MagazineCandidatePageGatheringService magazineCandidatePageGatheringService;

	private final CategoryApi categoryApi;

	private final StepCompletenessDecider stepCompletenessDecider;

	private List<PageHeader> pageHeaderList;

	private Iterator<PageHeader> pageHeaderIterator;

	private boolean initialized;

	public MagazineReader(MagazineCandidatePageGatheringService magazineCandidatePageGatheringService, CategoryApi categoryApi,
			StepCompletenessDecider stepCompletenessDecider) {
		this.magazineCandidatePageGatheringService = magazineCandidatePageGatheringService;
		this.categoryApi = categoryApi;
		this.stepCompletenessDecider = stepCompletenessDecider;
	}

	@Override
	public synchronized PageHeader read() throws Exception {
		initialize();
		return doRead();
	}

	@Override
	public int getSize() {
		initialize();
		return pageHeaderList.size();
	}

	private void initialize() {
		if (!initialized) {
			initializeSourceList();
			log.info("Initial size of magazine list: {}", pageHeaderList.size());
			createIterator();
			initialized = true;
		}
	}

	private PageHeader doRead() {
		return pageHeaderIterator.hasNext() ? pageHeaderIterator.next() : null;
	}

	private void createIterator() {
		pageHeaderIterator = pageHeaderList.iterator();
	}

	private void initializeSourceList() {
		if (stepCompletenessDecider.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_MAGAZINES)) {
			pageHeaderList = Lists.newArrayList();
		} else if (!magazineCandidatePageGatheringService.isEmpty()) {
			pageHeaderList = magazineCandidatePageGatheringService.getAllPageHeadersThenClean();
		} else {
			initializeFromApi();
		}
	}

	private void initializeFromApi() {
		List<PageHeader> magazineList = Lists.newArrayList();

		magazineList.addAll(categoryApi.getPagesIncludingSubcategories(CategoryTitle.MAGAZINES, MediaWikiSource.MEMORY_ALPHA_EN));

		pageHeaderList = SortingUtil.sortedUnique(magazineList);
	}
}
