package com.cezarykluczynski.stapi.model.material.repository;

import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;
import com.cezarykluczynski.stapi.model.material.dto.MaterialRequestDTO;
import com.cezarykluczynski.stapi.model.material.entity.Material;

public interface MaterialRepositoryCustom extends CriteriaMatcher<MaterialRequestDTO, Material> {
}
