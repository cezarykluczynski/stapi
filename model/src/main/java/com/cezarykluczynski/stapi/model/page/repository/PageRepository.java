package com.cezarykluczynski.stapi.model.page.repository;

import com.cezarykluczynski.stapi.model.page.entity.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageRepository extends JpaRepository<Page, Long> {
}
