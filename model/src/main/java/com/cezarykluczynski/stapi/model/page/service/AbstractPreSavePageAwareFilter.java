package com.cezarykluczynski.stapi.model.page.service;

import com.cezarykluczynski.stapi.model.page.entity.Page;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.List;
import java.util.Map;

abstract class AbstractPreSavePageAwareFilter {

	Map<Long, List<Pair<Page, PageAware>>> flattenPages(Collection<PageAware> pageAwareList) {
		Map<Long, List<Pair<Page, PageAware>>> map = Maps.newHashMap();
		pageAwareList.forEach(pageAware -> {
			Long pageId = pageAware.getPage().getPageId();
			if (!map.containsKey(pageId)) {
				map.put(pageId, Lists.newArrayList());
			}
			map.get(pageId).add(Pair.of(pageAware.getPage(), pageAware));
		});

		return map;
	}

}
