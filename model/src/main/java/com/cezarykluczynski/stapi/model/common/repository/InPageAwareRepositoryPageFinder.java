package com.cezarykluczynski.stapi.model.common.repository;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.page.entity.Page;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class InPageAwareRepositoryPageFinder {

	private JpaContext jpaContext;

	private Map<Class<? extends PageAware>, PageAwareQueryBuilderFactory> factoryCache = Maps.newHashMap();

	@Inject
	public InPageAwareRepositoryPageFinder(JpaContext jpaContext) {
		this.jpaContext = jpaContext;
	}

	public List<Page> findByPagePageIdIn(Set<Long> pageIds, Class<? extends PageAware> baseClass) {
		PageAwareQueryBuilderFactory adHocQueryBuildeFactory = getQueryBuilderByClass(baseClass);

		PageRequest pageRequest = new PageRequest(0, 100);
		QueryBuilder<PageAware> pageAwareQueryBuilder = adHocQueryBuildeFactory.createQueryBuilder(pageRequest);

		pageAwareQueryBuilder.joinIn("page", "pageId", pageIds, Page.class);
		List<PageAware> pageAwareList = pageAwareQueryBuilder.findAll();

		if (CollectionUtils.isEmpty(pageAwareList)) {
			return Lists.newArrayList();
		}

		return pageAwareList.stream().map(PageAware::getPage).collect(Collectors.toList());
	}

	private PageAwareQueryBuilderFactory getQueryBuilderByClass(Class<? extends PageAware> baseClass) {
		if (!factoryCache.containsKey(baseClass)) {
			factoryCache.put(baseClass, new PageAwareQueryBuilderFactory(jpaContext, baseClass));
		}

		return factoryCache.get(baseClass);
	}

}
