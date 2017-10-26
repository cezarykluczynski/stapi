package com.cezarykluczynski.stapi.etl.template.hologram.processor;

import com.cezarykluczynski.stapi.etl.character.common.dto.CharacterRelationCacheKey;
import com.cezarykluczynski.stapi.etl.character.common.dto.CharacterRelationsMap;
import com.cezarykluczynski.stapi.etl.character.common.service.CharactersRelationsCache;
import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplateEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.character.dto.CharacterTemplate;
import com.cezarykluczynski.stapi.etl.template.character.processor.CharacterTemplateActorLinkingEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.DateStatusProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.StatusProcessor;
import com.cezarykluczynski.stapi.etl.template.hologram.dto.HologramTemplateParameter;
import com.cezarykluczynski.stapi.etl.template.individual.processor.species.CharacterSpeciesWikitextProcessor;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HologramTemplateCompositeEnrichingProcessor implements ItemWithTemplateEnrichingProcessor<CharacterTemplate> {

	private final CharacterSpeciesWikitextProcessor characterSpeciesWikitextProcessor;

	private final CharactersRelationsCache charactersRelationsCache;

	private final HologramActivationDateProcessor hologramActivationDateProcessor;

	private final StatusProcessor statusProcessor;

	private final DateStatusProcessor dateStatusProcessor;

	private final CharacterTemplateActorLinkingEnrichingProcessor characterTemplateActorLinkingEnrichingProcessor;

	public HologramTemplateCompositeEnrichingProcessor(CharacterSpeciesWikitextProcessor characterSpeciesWikitextProcessor,
			CharactersRelationsCache charactersRelationsCache, HologramActivationDateProcessor hologramActivationDateProcessor,
			StatusProcessor statusProcessor, DateStatusProcessor dateStatusProcessor,
			CharacterTemplateActorLinkingEnrichingProcessor characterTemplateActorLinkingEnrichingProcessor) {
		this.characterSpeciesWikitextProcessor = characterSpeciesWikitextProcessor;
		this.charactersRelationsCache = charactersRelationsCache;
		this.hologramActivationDateProcessor = hologramActivationDateProcessor;
		this.statusProcessor = statusProcessor;
		this.dateStatusProcessor = dateStatusProcessor;
		this.characterTemplateActorLinkingEnrichingProcessor = characterTemplateActorLinkingEnrichingProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Template, CharacterTemplate> enrichablePair) throws Exception {
		Template sidebarHologramTemplate = enrichablePair.getInput();
		CharacterTemplate characterTemplate = enrichablePair.getOutput();

		characterTemplate.setHologram(true);

		for (Template.Part part : sidebarHologramTemplate.getParts()) {
			String key = part.getKey();
			String value = part.getValue();

			switch (key) {
				case HologramTemplateParameter.APPEARANCE:
				case HologramTemplateParameter.SPECIES:
					characterTemplate.getCharacterSpecies().addAll(characterSpeciesWikitextProcessor.process(Pair.of(value, characterTemplate)));
					break;
				case HologramTemplateParameter.CREATOR:
				case HologramTemplateParameter.SPOUSE:
				case HologramTemplateParameter.CHILDREN:
				case HologramTemplateParameter.RELATIVE:
					charactersRelationsCache.put(characterTemplate.getPage().getPageId(), CharacterRelationsMap
							.of(CharacterRelationCacheKey.of(TemplateTitle.SIDEBAR_HOLOGRAM, key), part));
					break;
				case HologramTemplateParameter.ACTIVATION_DATE:
					characterTemplate.setHologramActivationDate(hologramActivationDateProcessor.process(value));
					break;
				case HologramTemplateParameter.STATUS:
					characterTemplate.setHologramStatus(statusProcessor.process(value));
					break;
				case HologramTemplateParameter.DATE_STATUS:
					characterTemplate.setHologramDateStatus(dateStatusProcessor.process(value));
					break;
				case HologramTemplateParameter.ACTOR:
					characterTemplateActorLinkingEnrichingProcessor.enrich(EnrichablePair.of(value, characterTemplate));
					break;
				default:
					break;
			}
		}
	}

}
