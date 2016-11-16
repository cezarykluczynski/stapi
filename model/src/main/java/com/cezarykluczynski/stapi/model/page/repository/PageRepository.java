package com.cezarykluczynski.stapi.model.page.repository;

import com.cezarykluczynski.stapi.model.page.entity.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PageRepository extends JpaRepository<Page, Long> {

	List<Page> findByPageIdIn(Collection<Long> longCollection);

	Optional<Page> findByPageId(Long pageId);

}
