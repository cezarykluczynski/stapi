package com.cezarykluczynski.stapi.model.magazine.repository;

import com.cezarykluczynski.stapi.model.common.repository.PageAwareRepository;
import com.cezarykluczynski.stapi.model.magazine.entity.Magazine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MagazineRepository extends JpaRepository<Magazine, Long>, PageAwareRepository<Magazine>, MagazineRepositoryCustom {
}
