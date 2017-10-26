package com.cezarykluczynski.stapi.model.common.repository;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.page.entity.Page;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class InPageAwareRepositoryPageFinder {

	private final PageAwareQueryBuilderSingletonFactoryProducer pageAwareQueryBuilderSingletonFactoryProducer;

	public InPageAwareRepositoryPageFinder(PageAwareQueryBuilderSingletonFactoryProducer pageAwareQueryBuilderSingletonFactoryProducer) {
		this.pageAwareQueryBuilderSingletonFactoryProducer = pageAwareQueryBuilderSingletonFactoryProducer;
	}

	public synchronized List<Page> findByPagePageIdIn(Set<Long> pageIds, Class<? extends PageAware> baseClass) {
		PageAwareQueryBuilderFactory pageAwareQueryBuilderFactory = pageAwareQueryBuilderSingletonFactoryProducer.create(baseClass);

		PageRequest pageRequest = new PageRequest(0, 100);
		QueryBuilder<PageAware> pageAwareQueryBuilder = pageAwareQueryBuilderFactory.createQueryBuilder(pageRequest);

		pageAwareQueryBuilder.joinPageIdsIn(pageIds);
		pageAwareQueryBuilder.joinEquals("page", "mediaWikiSource", MediaWikiSource.MEMORY_ALPHA_EN, Page.class);
		List<PageAware> pageAwareList = pageAwareQueryBuilder.findAll();

		if (CollectionUtils.isEmpty(pageAwareList)) {
			return Lists.newArrayList();
		}

		return pageAwareList
				.stream()
				.map(PageAware::getPage)
				.collect(Collectors.toList());
	}

}
