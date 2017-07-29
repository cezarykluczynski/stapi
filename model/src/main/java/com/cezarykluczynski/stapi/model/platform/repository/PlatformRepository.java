package com.cezarykluczynski.stapi.model.platform.repository;

import com.cezarykluczynski.stapi.model.platform.entity.Platform;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlatformRepository extends JpaRepository<Platform, Long> {
}
