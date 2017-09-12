package com.cezarykluczynski.stapi.model.character.repository;

import com.cezarykluczynski.stapi.model.character.entity.CharacterRelation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRelationRepository extends JpaRepository<CharacterRelation, Long>, CharacterRelationRepositoryCustom {
}
