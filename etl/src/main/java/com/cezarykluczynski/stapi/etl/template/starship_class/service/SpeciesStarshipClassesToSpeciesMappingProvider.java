package com.cezarykluczynski.stapi.etl.template.starship_class.service;

import com.cezarykluczynski.stapi.model.species.entity.Species;
import com.cezarykluczynski.stapi.model.species.repository.SpeciesRepository;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class SpeciesStarshipClassesToSpeciesMappingProvider {

	private static final Map<String, String> MAPPINGS = Maps.newHashMap();
	private static final String STARSHIP_CLASSES_SUFFIX = "_starship_classes";

	static {
		MAPPINGS.put("Earth_starship_classes", "Human");
	}

	private final SpeciesRepository speciesRepository;

	private final Set<String> loggedFailedLookups = Sets.newHashSet();

	public SpeciesStarshipClassesToSpeciesMappingProvider(SpeciesRepository speciesRepository) {
		this.speciesRepository = speciesRepository;
	}

	public Optional<Species> provide(String starshipClassCategoryTitle) {
		String speciesName;
		if (MAPPINGS.containsKey(starshipClassCategoryTitle)) {
			speciesName = MAPPINGS.get(starshipClassCategoryTitle);
		} else {
			if (starshipClassCategoryTitle.endsWith(STARSHIP_CLASSES_SUFFIX)) {
				speciesName = starshipClassCategoryTitle.replace(STARSHIP_CLASSES_SUFFIX, "");
			} else {
				return Optional.empty();
			}
		}

		Optional<Species> speciesOptional = speciesRepository.findByName(speciesName);

		if (!speciesOptional.isPresent()) {
			if (!loggedFailedLookups.contains(speciesName)) {
				log.warn("Could not find species by name \"{}\" given in mappings", speciesName);
				loggedFailedLookups.add(speciesName);
			}
		}

		return speciesOptional;
	}

}
