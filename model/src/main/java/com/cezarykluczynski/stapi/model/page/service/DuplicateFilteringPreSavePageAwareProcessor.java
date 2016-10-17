package com.cezarykluczynski.stapi.model.page.service;

import com.cezarykluczynski.stapi.model.page.entity.Page;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.repository.PageRepository;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DuplicateFilteringPreSavePageAwareProcessor extends AbstractPageAttacher implements PreSavePageAwareProcessor {

	private PageRepository pageRepository;

	@Inject
	public DuplicateFilteringPreSavePageAwareProcessor(PageRepository pageRepository) {
		this.pageRepository = pageRepository;
	}

	@Override
	public List<PageAware> process(List<PageAware> pageAwareList) {
		List<Integer> indicesToRemove = Lists.newArrayList();

		Map<Long, List<Pair<Page, PageAware>>> flattenPages = flattenPages(pageAwareList);

		flattenPages.entrySet().forEach(longListEntry -> {
			List<Pair<Page, PageAware>> possibleDuplicates = longListEntry.getValue();
			if (possibleDuplicates.size() > 1) {
				Pair<Page, PageAware> uniqueEntry = possibleDuplicates.get(0);

				for (int i = 1; i < possibleDuplicates.size(); i++) {
					indicesToRemove.add(pageAwareList.indexOf(possibleDuplicates.get(i).getValue()));
				}

				possibleDuplicates.clear();
				possibleDuplicates.add(uniqueEntry);
			}
		});

		removeAtIndices(indicesToRemove, pageAwareList);
		indicesToRemove.clear();

		List<Page> pageList = pageRepository.findByPageIdIn(flattenPages.keySet());

		if (!pageList.isEmpty()) {
			List<Long> foundPagePageIdList = pageList.stream().map(Page::getPageId).collect(Collectors.toList());
			for (int i = 0; i < pageAwareList.size(); i++) {
				Long pageAwarePageId = pageAwareList.get(i).getPage().getPageId();
				if (foundPagePageIdList.contains(pageAwarePageId)) {
					indicesToRemove.add(i);
				}
			}

			removeAtIndices(indicesToRemove, pageAwareList);

			pageList.forEach(page -> {
				List<Pair<Page, PageAware>> pairList = flattenPages.get(page.getPageId());
				pairList.forEach(pair -> pair.getValue().setPage(page));
			});
		}

		return pageAwareList;
	}

	private void removeAtIndices(List<Integer> indicesToRemove, List<PageAware> pageAwareList) {
		for (int j = indicesToRemove.size() - 1; j >= 0; j--) {
			int index = indicesToRemove.get(j);
			PageAware pageAware = pageAwareList.get(index);
			log.info("Removing duplicated entity {} for pageId {} with title {}",
					pageAware, pageAware.getPage().getPageId(), pageAware.getPage().getTitle());
			pageAwareList.remove(index);
		}
	}

}
