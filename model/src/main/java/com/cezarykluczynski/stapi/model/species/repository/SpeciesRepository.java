package com.cezarykluczynski.stapi.model.species.repository;

import com.cezarykluczynski.stapi.model.species.entity.Species;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpeciesRepository extends JpaRepository<Species, Long> {
}
