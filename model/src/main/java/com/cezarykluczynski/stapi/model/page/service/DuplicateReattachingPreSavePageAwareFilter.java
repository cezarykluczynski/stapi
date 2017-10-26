package com.cezarykluczynski.stapi.model.page.service;

import com.cezarykluczynski.stapi.model.page.entity.Page;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.repository.PageRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class DuplicateReattachingPreSavePageAwareFilter extends AbstractPreSavePageAwareFilter implements PreSavePageAwareFilter {

	private final PageRepository pageRepository;

	public DuplicateReattachingPreSavePageAwareFilter(PageRepository pageRepository) {
		this.pageRepository = pageRepository;
	}

	@Override
	public List<PageAware> process(List<PageAware> pageAwareList, Class<? extends PageAware> baseClass) {
		Map<Long, List<Pair<Page, PageAware>>> flattenPages = flattenPages(pageAwareList);
		List<Page> pageList = pageRepository.findByPageIdIn(flattenPages.keySet());

		if (!pageList.isEmpty()) {
			log.info("Found {} Page entities to be attached: {}", pageList.size(), pageList);

			pageList.forEach(page -> {
				List<Pair<Page, PageAware>> pairList = flattenPages.get(page.getPageId());
				pairList.forEach(pair -> pair.getValue().setPage(page));
			});
		}

		return pageAwareList;
	}

}
