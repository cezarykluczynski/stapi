package com.cezarykluczynski.stapi.model.common.query;

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy;
import com.google.common.base.Preconditions;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaContext;

public abstract class AbstractQueryBuilderFactory<T> {

	private JpaContext jpaContext;

	private CachingStrategy cachingStrategy;

	private Class baseClass;

	protected AbstractQueryBuilderFactory() {}

	protected AbstractQueryBuilderFactory(JpaContext jpaContext, CachingStrategy cachingStrategy, Class baseClass) {
		Preconditions.checkNotNull(jpaContext, "JpaContext has to be set");
		Preconditions.checkNotNull(cachingStrategy, "CachingStrategy has to be set");
		Preconditions.checkNotNull(baseClass, "Base class has to be set");

		this.jpaContext = jpaContext;
		this.cachingStrategy = cachingStrategy;
		this.baseClass = baseClass;
	}

	public QueryBuilder<T> createQueryBuilder(Pageable pageable) {
		Preconditions.checkNotNull(pageable, "Pageable has to be set");

		return new QueryBuilder<>(jpaContext.getEntityManagerByManagedType(baseClass), cachingStrategy, baseClass, pageable);
	}

}
