package com.cezarykluczynski.stapi.etl.template.starship_class.service;

import com.cezarykluczynski.stapi.model.species.entity.Species;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SpeciesStarshipClassesToSpeciesMappingProvider {

	public Optional<Species> provide(String starshipClassCategoryTitle) {
		// TODO
		return Optional.empty();
	}

}
