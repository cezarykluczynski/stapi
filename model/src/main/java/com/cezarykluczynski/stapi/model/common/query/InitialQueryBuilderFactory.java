package com.cezarykluczynski.stapi.model.common.query;

import org.springframework.data.domain.Pageable;

public interface InitialQueryBuilderFactory<CRITERIA, T> {

	QueryBuilder<T> createInitialQueryBuilder(CRITERIA criteria, Pageable pageable);

}
