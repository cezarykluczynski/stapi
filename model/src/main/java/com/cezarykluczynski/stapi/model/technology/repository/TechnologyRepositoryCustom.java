package com.cezarykluczynski.stapi.model.technology.repository;

import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;
import com.cezarykluczynski.stapi.model.technology.dto.TechnologyRequestDTO;
import com.cezarykluczynski.stapi.model.technology.entity.Technology;

public interface TechnologyRepositoryCustom extends CriteriaMatcher<TechnologyRequestDTO, Technology> {
}
