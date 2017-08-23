package com.cezarykluczynski.stapi.model.spacecraft_type.repository;

import com.cezarykluczynski.stapi.model.common.repository.PageAwareRepository;
import com.cezarykluczynski.stapi.model.spacecraft_type.entity.SpacecraftType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpacecraftTypeRepository extends JpaRepository<SpacecraftType, Long>, PageAwareRepository<SpacecraftType> {

	Optional<SpacecraftType> findByNameIgnoreCase(String name);

}
