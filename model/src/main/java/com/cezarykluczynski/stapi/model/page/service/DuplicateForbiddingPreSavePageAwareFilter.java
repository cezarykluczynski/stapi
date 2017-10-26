package com.cezarykluczynski.stapi.model.page.service;

import com.cezarykluczynski.stapi.model.page.entity.Page;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DuplicateForbiddingPreSavePageAwareFilter extends AbstractPreSavePageAwareFilter implements PreSavePageAwareFilter {

	private final PageBoundToEntityFilteringFinder pageBoundToEntityFilteringFinder;

	public DuplicateForbiddingPreSavePageAwareFilter(PageBoundToEntityFilteringFinder pageBoundToEntityFilteringFinder) {
		this.pageBoundToEntityFilteringFinder = pageBoundToEntityFilteringFinder;
	}

	@Override
	public List<PageAware> process(List<PageAware> pageAwareList, Class<? extends PageAware> baseClass) {
		Map<Long, List<Pair<Page, PageAware>>> flattenPages = flattenPages(pageAwareList);
		flattenPages.entrySet().forEach(longListEntry -> {
			if (longListEntry.getValue().size() > 1) {
				throw new StapiRuntimeException(String.format("Duplicated page entries in chunk, pageId: %s, entities: %s ",
						longListEntry.getKey(), Lists.newArrayList(longListEntry.getValue().stream()
						.map(Pair::getRight)
						.collect(Collectors.toList()))));
			}
		});

		Set<Long> pagePageIds = flattenPages.keySet();

		List<Page> pageList = pageBoundToEntityFilteringFinder.find(pagePageIds, baseClass);

		if (!pageList.isEmpty()) {
			throw new StapiRuntimeException(String.format("Pages already persisted, pageIds are %s",
					pageList.stream().map(Page::getPageId).collect(Collectors.toList())));
		}

		return pageAwareList;
	}

}
