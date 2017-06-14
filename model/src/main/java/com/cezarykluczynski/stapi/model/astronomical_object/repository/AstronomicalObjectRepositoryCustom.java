package com.cezarykluczynski.stapi.model.astronomical_object.repository;

import com.cezarykluczynski.stapi.model.astronomical_object.dto.AstronomicalObjectRequestDTO;
import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;

public interface AstronomicalObjectRepositoryCustom extends CriteriaMatcher<AstronomicalObjectRequestDTO, AstronomicalObject> {
}
