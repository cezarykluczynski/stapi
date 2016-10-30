package com.cezarykluczynski.stapi.model.performer.repository;

import com.cezarykluczynski.stapi.model.performer.dto.PerformerRequestDTO;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PerformerRepositoryCustom {

	Page<Performer> findMatching(PerformerRequestDTO performerRequestDTO, Pageable pageable);

}
