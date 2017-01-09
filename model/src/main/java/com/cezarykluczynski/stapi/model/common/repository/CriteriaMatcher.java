package com.cezarykluczynski.stapi.model.common.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CriteriaMatcher<C, E> {

	Page<E> findMatching(C criteria, Pageable pageable);

}
