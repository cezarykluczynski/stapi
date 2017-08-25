package com.cezarykluczynski.stapi.model.spacecraft_class.repository;

import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;
import com.cezarykluczynski.stapi.model.spacecraft_class.dto.SpacecraftClassRequestDTO;
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass;

public interface SpacecraftClassRepositoryCustom extends CriteriaMatcher<SpacecraftClassRequestDTO, SpacecraftClass> {
}
