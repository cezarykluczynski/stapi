package com.cezarykluczynski.stapi.model.title.repository;

import com.cezarykluczynski.stapi.model.common.repository.PageAwareRepository;
import com.cezarykluczynski.stapi.model.title.entity.Title;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleRepository extends JpaRepository<Title, Long>, PageAwareRepository<Title>, TitleRepositoryCustom {
}
