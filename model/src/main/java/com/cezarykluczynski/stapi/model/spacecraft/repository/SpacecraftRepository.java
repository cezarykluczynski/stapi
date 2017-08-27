package com.cezarykluczynski.stapi.model.spacecraft.repository;

import com.cezarykluczynski.stapi.model.spacecraft.entity.Spacecraft;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpacecraftRepository extends JpaRepository<Spacecraft, Long> {
}
