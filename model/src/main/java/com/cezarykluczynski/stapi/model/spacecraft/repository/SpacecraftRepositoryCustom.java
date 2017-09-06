package com.cezarykluczynski.stapi.model.spacecraft.repository;

import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;
import com.cezarykluczynski.stapi.model.spacecraft.dto.SpacecraftRequestDTO;
import com.cezarykluczynski.stapi.model.spacecraft.entity.Spacecraft;

public interface SpacecraftRepositoryCustom extends CriteriaMatcher<SpacecraftRequestDTO, Spacecraft> {
}
