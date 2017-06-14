package com.cezarykluczynski.stapi.model.endpoint_hit.repository;

import com.cezarykluczynski.stapi.model.endpoint_hit.entity.EndpointHit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface EndpointHitRepository extends JpaRepository<EndpointHit, Long> {

	@Modifying
	@Transactional
	@Query("update EndpointHit eh set eh.numberOfHits = eh.numberOfHits + :numberOfHits where eh.endpointName = :endpointName "
			+ "and eh.methodName = :methodName")
	void updateNumberOfHits(@Param("numberOfHits") Long numberOfHits, @Param ("endpointName") String endpointName,
			@Param ("methodName") String methodName);

}
