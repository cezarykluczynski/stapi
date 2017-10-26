package com.cezarykluczynski.stapi.etl.template.starship_class.service;

import com.cezarykluczynski.stapi.model.species.entity.Species;
import com.cezarykluczynski.stapi.model.species.repository.SpeciesRepository;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class SpeciesStarshipClassesToSpeciesMappingProvider {

	private static final Map<String, String> MAPPINGS = Maps.newHashMap();

	static {
		MAPPINGS.put("Andorian_starship_classes", "Andorian");
		MAPPINGS.put("Bajoran_starship_classes", "Bajoran");
		MAPPINGS.put("Bolian_starship_classes", "Bolian");
		MAPPINGS.put("Cardassian_starship_classes", "Cardassian");
		MAPPINGS.put("Earth_starship_classes", "Human");
		MAPPINGS.put("Ferengi_starship_classes", "Ferengi");
		MAPPINGS.put("Hirogen_starship_classes", "Hirogen");
		MAPPINGS.put("Kazon_starship_classes", "Kazon");
		MAPPINGS.put("Klingon_starship_classes", "Klingon");
		MAPPINGS.put("Krenim_starship_classes", "Krenim");
		MAPPINGS.put("Orion_starship_classes", "Orion");
		MAPPINGS.put("Rigelian_starship_classes", "Rigelian");
		MAPPINGS.put("Romulan_starship_classes", "Romulan");
		MAPPINGS.put("Suliban_starship_classes", "Suliban");
		MAPPINGS.put("Talarian_starship_classes", "Talarian");
		MAPPINGS.put("Talaxian_starship_classes", "Talaxian");
		MAPPINGS.put("Tellarite_starship_classes", "Tellarite");
		MAPPINGS.put("Tholian_starship_classes", "Tholian");
		MAPPINGS.put("Vidiian_starship_classes", "Vidiian");
		MAPPINGS.put("Vulcan_starship_classes", "Vulcan");
		MAPPINGS.put("Yridian_starship_classes", "Yridian");
	}

	private final SpeciesRepository speciesRepository;

	public SpeciesStarshipClassesToSpeciesMappingProvider(SpeciesRepository speciesRepository) {
		this.speciesRepository = speciesRepository;
	}

	public Optional<Species> provide(String starshipClassCategoryTitle) {
		if (!MAPPINGS.containsKey(starshipClassCategoryTitle)) {
			return Optional.empty();
		}

		String speciesName = MAPPINGS.get(starshipClassCategoryTitle);
		Optional<Species> speciesOptional = speciesRepository.findByName(speciesName);

		if (!speciesOptional.isPresent()) {
			log.warn("Could not find species by name \"{}\" given in mappings", speciesName);
		}

		return speciesOptional;
	}

}
