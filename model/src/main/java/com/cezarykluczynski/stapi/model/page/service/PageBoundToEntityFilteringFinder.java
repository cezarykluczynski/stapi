package com.cezarykluczynski.stapi.model.page.service;

import com.cezarykluczynski.stapi.model.common.repository.InPageAwareRepositoryPageFinder;
import com.cezarykluczynski.stapi.model.page.entity.Page;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.repository.PageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PageBoundToEntityFilteringFinder {

	private final PageRepository pageRepository;

	private final InPageAwareRepositoryPageFinder inPageAwareRepositoryPageFinder;

	public PageBoundToEntityFilteringFinder(PageRepository pageRepository, InPageAwareRepositoryPageFinder inPageAwareRepositoryPageFinder) {
		this.pageRepository = pageRepository;
		this.inPageAwareRepositoryPageFinder = inPageAwareRepositoryPageFinder;
	}

	public List<Page> find(Set<Long> pagePageIds, Class<? extends PageAware> baseClass) {
		List<Long> baseClassPageIdList = inPageAwareRepositoryPageFinder.findByPagePageIdIn(pagePageIds, baseClass)
				.stream()
				.map(Page::getId)
				.collect(Collectors.toList());
		List<Page> pageList = pageRepository.findByPageIdIn(pagePageIds);
		return pageList
				.stream()
				.filter(page -> !baseClassPageIdList.contains(page.getId()))
				.collect(Collectors.toList());
	}
}
