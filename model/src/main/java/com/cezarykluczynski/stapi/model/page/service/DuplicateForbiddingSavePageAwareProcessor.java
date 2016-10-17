package com.cezarykluczynski.stapi.model.page.service;

import com.cezarykluczynski.stapi.model.page.entity.Page;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.repository.PageRepository;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DuplicateForbiddingSavePageAwareProcessor extends AbstractPageAttacher implements PreSavePageAwareProcessor {

	private PageRepository pageRepository;

	@Inject
	public DuplicateForbiddingSavePageAwareProcessor(PageRepository pageRepository) {
		this.pageRepository = pageRepository;
	}

	@Override
	public List<PageAware> process(List<PageAware> pageAwareList) {
		Map<Long, List<Pair<Page, PageAware>>> flattenPages = flattenPages(pageAwareList);
		flattenPages.entrySet().forEach(longListEntry -> {
			if (longListEntry.getValue().size() > 1) {
				throw new RuntimeException(String.format("Duplicated page entries in chunk, pageId: %s, entities: %s ",
						longListEntry.getKey(), Lists.newArrayList(longListEntry.getValue().stream()
						.map(Pair::getRight)
						.collect(Collectors.toList()))));
			}
		});

		Collection<Long> pagePageIds = flattenPages.keySet();

		List<Page> pageList = pageRepository.findByPageIdIn(pagePageIds);

		if (!pageList.isEmpty()) {
			throw new RuntimeException(String.format("Pages already persisted, pageIds are %s",
					pageList.stream().map(Page::getPageId).collect(Collectors.toList())));
		}

		return pageAwareList;
	}

}
