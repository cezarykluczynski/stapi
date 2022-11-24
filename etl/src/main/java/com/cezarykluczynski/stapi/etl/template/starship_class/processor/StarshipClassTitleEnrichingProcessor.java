package com.cezarykluczynski.stapi.etl.template.starship_class.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.starship_class.dto.StarshipClassTemplate;
import com.cezarykluczynski.stapi.model.species.repository.SpeciesRepository;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StarshipClassTitleEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<String, StarshipClassTemplate>> {

	private final SpeciesRepository speciesRepository;

	@Override
	public void enrich(EnrichablePair<String, StarshipClassTemplate> enrichablePair) throws Exception {
		final String title = enrichablePair.getInput();
		final StarshipClassTemplate starshipClassTemplate = enrichablePair.getOutput();
		if (starshipClassTemplate.getSpecies() != null) {
			return;
		}

		List<String> words = Lists.newArrayList(title.split("\\s"));
		if (words.size() < 2) {
			return;
		}

		String speciesCandidate = words.get(0);
		speciesRepository.findByName(speciesCandidate).ifPresent(starshipClassTemplate::setSpecies);
	}
}
