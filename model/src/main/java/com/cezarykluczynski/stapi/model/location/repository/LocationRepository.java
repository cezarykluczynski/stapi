package com.cezarykluczynski.stapi.model.location.repository;

import com.cezarykluczynski.stapi.model.common.repository.PageAwareRepository;
import com.cezarykluczynski.stapi.model.location.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long>, PageAwareRepository<Location>, LocationRepositoryCustom {
}
