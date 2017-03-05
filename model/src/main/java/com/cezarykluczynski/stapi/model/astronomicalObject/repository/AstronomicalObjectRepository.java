package com.cezarykluczynski.stapi.model.astronomicalObject.repository;

import com.cezarykluczynski.stapi.model.astronomicalObject.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.model.common.repository.PageAwareRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AstronomicalObjectRepository extends JpaRepository<AstronomicalObject, Long>, PageAwareRepository<AstronomicalObject>,
		AstronomicalObjectRepositoryCustom {
}
