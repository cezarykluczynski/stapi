package com.cezarykluczynski.stapi.model.platform.repository;

import com.cezarykluczynski.stapi.model.platform.entity.Platform;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlatformRepository extends JpaRepository<Platform, Long> {

	Optional<Platform> findByName(String name);

}
