package com.cezarykluczynski.stapi.model.occupation.repository;

import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;
import com.cezarykluczynski.stapi.model.occupation.dto.OccupationRequestDTO;
import com.cezarykluczynski.stapi.model.occupation.entity.Occupation;

public interface OccupationRepositoryCustom extends CriteriaMatcher<OccupationRequestDTO, Occupation> {
}
