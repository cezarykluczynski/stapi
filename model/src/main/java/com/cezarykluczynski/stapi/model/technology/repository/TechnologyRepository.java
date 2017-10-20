package com.cezarykluczynski.stapi.model.technology.repository;

import com.cezarykluczynski.stapi.model.technology.entity.Technology;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechnologyRepository extends JpaRepository<Technology, Long>, TechnologyRepositoryCustom {
}
