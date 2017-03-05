package com.cezarykluczynski.stapi.model.character.repository;

import com.cezarykluczynski.stapi.model.character.entity.CharacterSpecies;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterSpeciesRepository extends JpaRepository<CharacterSpecies, Long>, CharacterSpeciesRepositoryCustom {
}
