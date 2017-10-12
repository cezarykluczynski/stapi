package com.cezarykluczynski.stapi.model.conflict.repository;

import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;
import com.cezarykluczynski.stapi.model.conflict.dto.ConflictRequestDTO;
import com.cezarykluczynski.stapi.model.conflict.entity.Conflict;

public interface ConflictRepositoryCustom extends CriteriaMatcher<ConflictRequestDTO, Conflict> {
}
