package com.cezarykluczynski.stapi.model.spacecraft_class.repository;

import com.cezarykluczynski.stapi.model.common.repository.PageAwareRepository;
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpacecraftClassRepository extends JpaRepository<SpacecraftClass, Long>, PageAwareRepository<SpacecraftClass>,
		SpacecraftClassRepositoryCustom {
}
