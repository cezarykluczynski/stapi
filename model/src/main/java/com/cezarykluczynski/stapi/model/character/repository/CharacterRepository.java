package com.cezarykluczynski.stapi.model.character.repository;

import com.cezarykluczynski.stapi.model.character.entity.Character;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CharacterRepository extends JpaRepository<Character, Long>, CharacterRepositoryCustom {

	Optional<Character> findByName(String name);

	Optional<Character> findByPagePageId(Long pageId);

}
