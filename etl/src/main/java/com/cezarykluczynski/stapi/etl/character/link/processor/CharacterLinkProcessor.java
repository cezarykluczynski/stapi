package com.cezarykluczynski.stapi.etl.character.link.processor;

import com.cezarykluczynski.stapi.etl.character.common.dto.CharacterRelationsMap;
import com.cezarykluczynski.stapi.etl.character.common.service.CharactersRelationsCache;
import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.mapper.MediaWikiSourceMapper;
import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor;
import com.cezarykluczynski.stapi.etl.util.constant.StepName;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.character.repository.CharacterRepository;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CharacterLinkProcessor implements ItemProcessor<PageHeader, Character> {

	private final CharactersRelationsCache charactersRelationsCache;

	private final CharacterRepository characterRepository;

	private final PageHeaderProcessor pageHeaderProcessor;

	private final CharacterLinkCacheStoringProcessor characterLinkCacheStoringProcessor;

	private final CharacterLinkRelationsEnrichingProcessor characterLinkRelationsEnrichingProcessor;

	private final MediaWikiSourceMapper mediaWikiSourceMapper;

	private boolean validated;

	public CharacterLinkProcessor(CharactersRelationsCache charactersRelationsCache, CharacterRepository characterRepository,
			PageHeaderProcessor pageHeaderProcessor, CharacterLinkCacheStoringProcessor characterLinkCacheStoringProcessor,
			CharacterLinkRelationsEnrichingProcessor characterLinkRelationsEnrichingProcessor, MediaWikiSourceMapper mediaWikiSourceMapper) {
		this.charactersRelationsCache = charactersRelationsCache;
		this.characterRepository = characterRepository;
		this.pageHeaderProcessor = pageHeaderProcessor;
		this.characterLinkCacheStoringProcessor = characterLinkCacheStoringProcessor;
		this.characterLinkRelationsEnrichingProcessor = characterLinkRelationsEnrichingProcessor;
		this.mediaWikiSourceMapper = mediaWikiSourceMapper;
	}

	@Override
	public Character process(PageHeader item) throws Exception {
		validate();
		CharacterRelationsMap characterRelationsMap = getCharacterRelationsMap(item);
		return characterRelationsMap == null ? null : getMarchingEnrichedCharacter(item, characterRelationsMap);
	}

	private Character getMarchingEnrichedCharacter(PageHeader item, CharacterRelationsMap characterRelationsMap) throws Exception {
		Optional<Character> characterOptional = characterRepository.findByPagePageIdAndPageMediaWikiSource(item.getPageId(),
				mediaWikiSourceMapper.fromSourcesToEntity(item.getMediaWikiSource()));

		if (characterOptional.isPresent()) {
			Character character = characterOptional.get();
			characterLinkRelationsEnrichingProcessor.enrich(EnrichablePair.of(characterRelationsMap, character));
			return character;
		}

		return null;
	}

	private CharacterRelationsMap getCharacterRelationsMap(PageHeader item) throws Exception {
		if (item == null) {
			return null;
		}

		CharacterRelationsMap characterRelationsMap = charactersRelationsCache.get(item.getPageId());

		if (characterRelationsMap == null) {
			characterRelationsMap = createCharacterRelationsMapUsingApi(item);
			if (characterRelationsMap == null) {
				return null;
			}

			charactersRelationsCache.put(item.getPageId(), characterRelationsMap);
		}
		return characterRelationsMap;
	}

	private CharacterRelationsMap createCharacterRelationsMapUsingApi(PageHeader item) throws Exception {
		Page page = pageHeaderProcessor.process(item);
		return page == null ? null : Optional.ofNullable(characterLinkCacheStoringProcessor.process(page)).orElse(null);
	}

	private void validate() {
		if (validated) {
			return;
		}

		if (charactersRelationsCache.isEmpty() && characterRepository.count() == 0) {
			throw new StapiRuntimeException(String.format("%s step cannot be run before %s step",
					StepName.LINK_CHARACTERS, StepName.CREATE_CHARACTERS));
		}

		validated = true;
	}

}
