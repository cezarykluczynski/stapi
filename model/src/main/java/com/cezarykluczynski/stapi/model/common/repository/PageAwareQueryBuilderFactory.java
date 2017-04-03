package com.cezarykluczynski.stapi.model.common.repository;

import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.common.query.CachingStrategy;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import org.springframework.data.jpa.repository.JpaContext;

class PageAwareQueryBuilderFactory extends AbstractQueryBuilderFactory<PageAware> {

	PageAwareQueryBuilderFactory(JpaContext jpaContext, CachingStrategy cachingStrategy, Class baseClass) {
		super(jpaContext, cachingStrategy, baseClass);
	}

}
