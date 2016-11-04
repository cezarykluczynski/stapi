package com.cezarykluczynski.stapi.model.performer.repository;

import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;
import com.cezarykluczynski.stapi.model.performer.dto.PerformerRequestDTO;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;

public interface PerformerRepositoryCustom extends CriteriaMatcher<PerformerRequestDTO, Performer> {
}
