package com.cezarykluczynski.stapi.model.throttle.repository;

import com.cezarykluczynski.stapi.model.throttle.entity.Throttle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ThrottleRepository extends JpaRepository<Throttle, Long> {

	@Query("update Throttle t set t.remainingHits = t.remainingHits - 1, t.lastHitTime = :lastHitTime where t.ipAddress = :ipAddress")
	void decrementByIp(String ipAddress, LocalDateTime lastHitTime);

	Optional<Throttle> findByIpAddress(String ipAddress);


}
