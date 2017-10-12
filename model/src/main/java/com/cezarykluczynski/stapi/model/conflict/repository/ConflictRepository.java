package com.cezarykluczynski.stapi.model.conflict.repository;

import com.cezarykluczynski.stapi.model.common.repository.PageAwareRepository;
import com.cezarykluczynski.stapi.model.conflict.entity.Conflict;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConflictRepository extends JpaRepository<Conflict, Long>, PageAwareRepository<Conflict>, ConflictRepositoryCustom {
}
