package com.cezarykluczynski.stapi.model.species.repository;

import com.cezarykluczynski.stapi.model.common.repository.PageAwareRepository;
import com.cezarykluczynski.stapi.model.species.entity.Species;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpeciesRepository extends JpaRepository<Species, Long>, PageAwareRepository<Species>, SpeciesRepositoryCustom {

	Optional<Species> findByName(String name);

}
