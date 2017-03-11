package com.cezarykluczynski.stapi.model.character.repository;

import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.character.entity.CharacterSpecies;
import com.cezarykluczynski.stapi.model.common.repository.PageAwareRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CharacterRepository extends JpaRepository<Character, Long>, PageAwareRepository<Character>, CharacterRepositoryCustom {

	Set<Character> findByCharacterSpeciesIn(Set<CharacterSpecies> characterSpecies);

}
