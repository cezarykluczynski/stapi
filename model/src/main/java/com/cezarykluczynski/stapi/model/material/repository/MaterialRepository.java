package com.cezarykluczynski.stapi.model.material.repository;

import com.cezarykluczynski.stapi.model.material.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<Material, Long>, MaterialRepositoryCustom {
}
