package com.cezarykluczynski.stapi.etl.common.service;

import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.character.repository.CharacterRepository;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.model.performer.repository.PerformerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

// This class encapsulates EntityLookupByNameService so that when no result is found in cache, empty optional is returned,
// but when there is a result, a fresh instance of a given entity is grabbed, to avoid LazyInitializationException down the line,
// and other problems stemming from using stale entities.
@Service
@RequiredArgsConstructor
public class EntityRefreshingLookupByNameService {

	private final EntityLookupByNameService entityLookupByNameService;

	private final PerformerRepository performerRepository;

	private final CharacterRepository characterRepository;

	public Optional<Performer> findPerformerByName(String performerName, MediaWikiSource mediaWikiSource) {
		return entityLookupByNameService.findPerformerByName(performerName, mediaWikiSource)
				.flatMap(performer -> performerRepository.findById(performer.getId()));
	}

	public Optional<Character> findCharacterByName(String characterName, MediaWikiSource mediaWikiSource) {
		return entityLookupByNameService.findCharacterByName(characterName, mediaWikiSource)
				.flatMap(performer -> characterRepository.findById(performer.getId()));
	}

}
