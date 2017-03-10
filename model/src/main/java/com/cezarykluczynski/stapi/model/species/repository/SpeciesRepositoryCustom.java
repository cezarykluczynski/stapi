package com.cezarykluczynski.stapi.model.species.repository;

import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;
import com.cezarykluczynski.stapi.model.species.dto.SpeciesRequestDTO;
import com.cezarykluczynski.stapi.model.species.entity.Species;

public interface SpeciesRepositoryCustom extends CriteriaMatcher<SpeciesRequestDTO, Species> {
}
