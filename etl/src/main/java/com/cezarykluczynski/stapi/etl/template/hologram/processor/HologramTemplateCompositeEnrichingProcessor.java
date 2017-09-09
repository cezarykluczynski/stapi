package com.cezarykluczynski.stapi.etl.template.hologram.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplateEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.character.dto.CharacterTemplate;
import com.cezarykluczynski.stapi.etl.template.character.processor.CharacterTemplateActorLinkingEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.hologram.dto.HologramTemplateParameter;
import com.cezarykluczynski.stapi.etl.template.individual.processor.species.CharacterSpeciesWikitextProcessor;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Set;

@Service
@Slf4j
public class HologramTemplateCompositeEnrichingProcessor implements ItemWithTemplateEnrichingProcessor<CharacterTemplate> {

	private static final Set<String> CHARACTER_SPECIES_DUPLICATES = Sets.newHashSet(HologramTemplateParameter.APPEARANCE,
			HologramTemplateParameter.SPECIES);

	private final CharacterSpeciesWikitextProcessor characterSpeciesWikitextProcessor;

	private final CharacterTemplateActorLinkingEnrichingProcessor characterTemplateActorLinkingEnrichingProcessor;

	@Inject
	public HologramTemplateCompositeEnrichingProcessor(CharacterSpeciesWikitextProcessor characterSpeciesWikitextProcessor,
			CharacterTemplateActorLinkingEnrichingProcessor characterTemplateActorLinkingEnrichingProcessor) {
		this.characterSpeciesWikitextProcessor = characterSpeciesWikitextProcessor;
		this.characterTemplateActorLinkingEnrichingProcessor = characterTemplateActorLinkingEnrichingProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Template, CharacterTemplate> enrichablePair) throws Exception {
		Template sidebarHologramTemplate = enrichablePair.getInput();
		CharacterTemplate characterTemplate = enrichablePair.getOutput();

		characterTemplate.setHologram(true);

		if (sidebarHologramTemplate.getParts().stream()
				.map(Template.Part::getValue)
				.filter(CHARACTER_SPECIES_DUPLICATES::contains)
				.count() > 1) {
			log.warn("More than one species related keys found for hologram {}", characterTemplate.getName());
		}

		for (Template.Part part : sidebarHologramTemplate.getParts()) {
			String key = part.getKey();
			String value = part.getValue();

			switch (key) {
				case HologramTemplateParameter.APPEARANCE:
				case HologramTemplateParameter.SPECIES:
					characterTemplate.getCharacterSpecies().addAll(characterSpeciesWikitextProcessor.process(Pair.of(value, characterTemplate)));
					break;
				case HologramTemplateParameter.CREATOR:
				case HologramTemplateParameter.ACTIVATION:
				case HologramTemplateParameter.STATUS:
				case HologramTemplateParameter.DATE_STATUS:
					// TODO
				case HologramTemplateParameter.ACTOR:
					characterTemplateActorLinkingEnrichingProcessor.enrich(EnrichablePair.of(part, characterTemplate));
					break;
				default:
					break;
			}
		}
	}

}
