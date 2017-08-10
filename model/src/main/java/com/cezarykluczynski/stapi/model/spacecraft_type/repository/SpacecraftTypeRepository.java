package com.cezarykluczynski.stapi.model.spacecraft_type.repository;

import com.cezarykluczynski.stapi.model.spacecraft_type.entity.SpacecraftType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpacecraftTypeRepository extends JpaRepository<SpacecraftType, Long> {
}
