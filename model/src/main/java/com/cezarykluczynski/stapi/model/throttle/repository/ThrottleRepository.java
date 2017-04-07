package com.cezarykluczynski.stapi.model.throttle.repository;

import com.cezarykluczynski.stapi.model.throttle.entity.Throttle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ThrottleRepository extends JpaRepository<Throttle, Long>, ThrottleRepositoryCustom {

	@Modifying
	@Transactional
	@Query("update Throttle t set t.remainingHits = t.remainingHits - 1, t.lastHitTime = :lastHitTime where t.ipAddress = :ipAddress")
	void decrementByIp(@Param("ipAddress") String ipAddress, @Param("lastHitTime") LocalDateTime lastHitTime);

	Optional<Throttle> findByIpAddress(String ipAddress);


}
