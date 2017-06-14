package com.cezarykluczynski.stapi.model.astronomical_object.repository;

import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.model.common.repository.PageAwareRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AstronomicalObjectRepository extends JpaRepository<AstronomicalObject, Long>, PageAwareRepository<AstronomicalObject>,
		AstronomicalObjectRepositoryCustom {
}
