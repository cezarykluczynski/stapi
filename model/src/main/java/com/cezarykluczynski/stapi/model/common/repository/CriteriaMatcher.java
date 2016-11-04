package com.cezarykluczynski.stapi.model.common.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CriteriaMatcher<CRITERIA, ENTITY> {

	Page<ENTITY> findMatching(CRITERIA criteria, Pageable pageable);

}
