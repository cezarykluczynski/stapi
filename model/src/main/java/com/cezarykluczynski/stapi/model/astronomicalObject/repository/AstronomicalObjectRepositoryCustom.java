package com.cezarykluczynski.stapi.model.astronomicalObject.repository;

import com.cezarykluczynski.stapi.model.astronomicalObject.dto.AstronomicalObjectRequestDTO;
import com.cezarykluczynski.stapi.model.astronomicalObject.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;

public interface AstronomicalObjectRepositoryCustom extends CriteriaMatcher<AstronomicalObjectRequestDTO, AstronomicalObject> {
}
