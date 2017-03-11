package com.cezarykluczynski.stapi.model.character.repository;

import com.cezarykluczynski.stapi.model.character.entity.CharacterSpecies;
import com.cezarykluczynski.stapi.model.species.entity.Species;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CharacterSpeciesRepository extends JpaRepository<CharacterSpecies, Long>, CharacterSpeciesRepositoryCustom {

	Set<CharacterSpecies> findBySpecies(Species species);

}
