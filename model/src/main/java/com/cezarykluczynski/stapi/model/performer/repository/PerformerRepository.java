package com.cezarykluczynski.stapi.model.performer.repository;

import com.cezarykluczynski.stapi.model.common.repository.PageAwareRepository;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformerRepository extends JpaRepository<Performer, Long>, PageAwareRepository<Performer>, PerformerRepositoryCustom {
}
