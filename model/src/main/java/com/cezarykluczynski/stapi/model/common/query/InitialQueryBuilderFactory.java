package com.cezarykluczynski.stapi.model.common.query;

import org.springframework.data.domain.Pageable;

public interface InitialQueryBuilderFactory<C, T> {

	QueryBuilder<T> createInitialQueryBuilder(C criteria, Pageable pageable);

}
