package com.cezarykluczynski.stapi.model.page.service;

import com.cezarykluczynski.stapi.model.common.repository.InPageAwareRepositoryPageFinder;
import com.cezarykluczynski.stapi.model.page.entity.Page;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DuplicateFilteringPreSavePageAwareFilter extends AbstractPreSavePageAwareFilter implements PreSavePageAwareFilter {

	private final InPageAwareRepositoryPageFinder inPageAwareRepositoryPageFinder;

	public DuplicateFilteringPreSavePageAwareFilter(InPageAwareRepositoryPageFinder inPageAwareRepositoryPageFinder) {
		this.inPageAwareRepositoryPageFinder = inPageAwareRepositoryPageFinder;
	}

	@Override
	public List<PageAware> process(List<PageAware> pageAwareList, Class<? extends PageAware> baseClass) {
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

		Set<Long> pageIds = flattenPages.keySet();

		List<Page> pageList = pageIds.isEmpty() ? Lists.newArrayList() : inPageAwareRepositoryPageFinder.findByPagePageIdIn(pageIds, baseClass);

		if (!pageList.isEmpty()) {
			List<Long> foundPagePageIdList = pageList
					.stream()
					.map(Page::getPageId)
					.collect(Collectors.toList());

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
		List<Integer> sortedIndicesToRemove = indicesToRemove
				.stream()
				.sorted()
				.collect(Collectors.toList());

		for (int j = sortedIndicesToRemove.size() - 1; j >= 0; j--) {
			int index = sortedIndicesToRemove.get(j);
			pageAwareList.remove(index);
		}
	}

}
