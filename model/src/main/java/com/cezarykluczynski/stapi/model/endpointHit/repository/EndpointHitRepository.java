package com.cezarykluczynski.stapi.model.endpointHit.repository;

import com.cezarykluczynski.stapi.model.endpointHit.entity.EndpointHit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EndpointHitRepository extends JpaRepository<EndpointHit, Long> {
}
