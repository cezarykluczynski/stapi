package com.cezarykluczynski.stapi.model.common.query;

import com.google.common.base.Preconditions;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaContext;

public abstract class AbstractQueryBuilderFactory<T> {

	private JpaContext jpaContext;

	private Class baseClass;

	protected AbstractQueryBuilderFactory() {
	}

	protected AbstractQueryBuilderFactory(JpaContext jpaContext, Class baseClass) {
		Preconditions.checkNotNull(jpaContext, "JpaContext has to be set");
		Preconditions.checkNotNull(baseClass, "Base class has to be set");

		this.jpaContext = jpaContext;
		this.baseClass = baseClass;
	}

	public QueryBuilder<T> createQueryBuilder(Pageable pageable) {
		Preconditions.checkNotNull(pageable, "Pageable has to be set");

		return new QueryBuilder(jpaContext.getEntityManagerByManagedType(baseClass), baseClass, pageable);
	}

}
