package com.cezarykluczynski.stapi.etl.template.individual.processor.species;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.common.service.EntityLookupByNameService;
import com.cezarykluczynski.stapi.model.character.entity.CharacterSpecies;
import com.cezarykluczynski.stapi.model.character.repository.CharacterSpeciesRepository;
import com.cezarykluczynski.stapi.model.species.entity.Species;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import org.apache.commons.lang3.math.Fraction;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class CharacterSpeciesWithSpeciesNameEnrichingProcessor implements
		ItemEnrichingProcessor<EnrichablePair<Pair<String, Fraction>, Set<CharacterSpecies>>> {

	private final CharacterSpeciesRepository characterSpeciesRepository;

	private final EntityLookupByNameService entityLookupByNameService;

	public CharacterSpeciesWithSpeciesNameEnrichingProcessor(CharacterSpeciesRepository characterSpeciesRepository,
			EntityLookupByNameService entityLookupByNameService) {
		this.characterSpeciesRepository = characterSpeciesRepository;
		this.entityLookupByNameService = entityLookupByNameService;
	}

	@Override
	public void enrich(EnrichablePair<Pair<String, Fraction>, Set<CharacterSpecies>> enrichablePair) throws Exception {
		String title = enrichablePair.getInput().getKey();
		Fraction fraction = enrichablePair.getInput().getRight();
		Set<CharacterSpecies> characterSpeciesSet = enrichablePair.getOutput();

		Optional<Species> speciesOptional = speciesFromName(title);
		speciesOptional.ifPresent(species -> {
			CharacterSpecies characterSpecies = characterSpeciesRepository.findOrCreate(species, fraction);
			characterSpeciesSet.add(characterSpecies);
		});
	}

	private Optional<Species> speciesFromName(String speciesName) {
		return entityLookupByNameService.findSpeciesByName(speciesName, MediaWikiSource.MEMORY_ALPHA_EN);
	}

}
