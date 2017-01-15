package com.cezarykluczynski.stapi.model.character.repository;

import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CharacterRepository extends JpaRepository<Character, Long>, CharacterRepositoryCustom {

	Optional<Character> findByPageTitleAndPageMediaWikiSource(String title, MediaWikiSource mediaWikiSource);

	Optional<Character> findByPagePageIdAndPageMediaWikiSource(Long pageId, MediaWikiSource mediaWikiSource);

}
