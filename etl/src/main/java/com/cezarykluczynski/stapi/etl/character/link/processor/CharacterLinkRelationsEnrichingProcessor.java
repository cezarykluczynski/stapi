package com.cezarykluczynski.stapi.etl.character.link.processor;

import com.cezarykluczynski.stapi.etl.character.common.dto.CharacterRelationsMap;
import com.cezarykluczynski.stapi.etl.character.link.relation.dto.CharacterPageLinkWithRelationName;
import com.cezarykluczynski.stapi.etl.character.link.relation.service.CharacterLinkExtendedRelationsExtractor;
import com.cezarykluczynski.stapi.etl.character.link.relation.service.CharacterRelationFactory;
import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.character.entity.CharacterRelation;
import com.cezarykluczynski.stapi.model.character.repository.CharacterRelationRepository;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CharacterLinkRelationsEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<CharacterRelationsMap, Character>> {

	private final CharacterLinkExtendedRelationsExtractor characterLinkExtendedRelationsExtractor;

	private final CharacterRelationFactory characterRelationFactory;

	private final CharacterRelationRepository characterRelationRepository;

	public CharacterLinkRelationsEnrichingProcessor(CharacterLinkExtendedRelationsExtractor characterLinkExtendedRelationsExtractor,
			CharacterRelationFactory characterRelationFactory, CharacterRelationRepository characterRelationRepository) {
		this.characterLinkExtendedRelationsExtractor = characterLinkExtendedRelationsExtractor;
		this.characterRelationFactory = characterRelationFactory;
		this.characterRelationRepository = characterRelationRepository;
	}

	@Override
	public void enrich(EnrichablePair<CharacterRelationsMap, Character> enrichablePair) throws Exception {
		CharacterRelationsMap characterRelationsMap = enrichablePair.getInput();
		Character character = enrichablePair.getOutput();
		List<CharacterRelation> characterRelationList = Lists.newArrayList();

		characterRelationsMap.forEach((characterRelationCacheKey, part) -> {
			if (part.getValue() != null) {
				List<CharacterPageLinkWithRelationName> characterPageLinkWithRelationNameList = characterLinkExtendedRelationsExtractor
						.extract(part.getValue());

				for (CharacterPageLinkWithRelationName characterPageLinkWithRelationName : characterPageLinkWithRelationNameList) {
					CharacterRelation characterRelation = characterRelationFactory
							.create(character, characterPageLinkWithRelationName, characterRelationCacheKey);

					if (characterRelation != null) {
						characterRelationList.add(characterRelation);
					}
				}
			}
		});

		characterRelationRepository.linkAndSave(characterRelationList);
	}

}
