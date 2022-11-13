package com.cezarykluczynski.stapi.etl.astronomical_object.link.processor;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.interfaces.FixedValueProvider;
import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.model.astronomical_object.repository.AstronomicalObjectRepository;
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AstronomicalObjectLocationFixedValueProvider implements FixedValueProvider<AstronomicalObject, AstronomicalObject> {

	private static final Map<String, String> LOCATIONS = Maps.newLinkedHashMap();

	static {
		LOCATIONS.put("Alpha Quadrant", "Milky Way Galaxy");
		LOCATIONS.put("Beta Quadrant", "Milky Way Galaxy");
		LOCATIONS.put("Delta Quadrant", "Milky Way Galaxy");
		LOCATIONS.put("Gamma Quadrant", "Milky Way Galaxy");
		LOCATIONS.put("Bassen Rift", "Beta Quadrant");
		LOCATIONS.put("Badlands", "Alpha Quadrant");
		LOCATIONS.put("Burke", "Sol system");
		LOCATIONS.put("Cambra system", "Beta Quadrant");
		LOCATIONS.put("Creation point", "Galactic core");
		LOCATIONS.put("D'Arrest", "Sol system");
		LOCATIONS.put("Delphic Expanse", "Beta Quadrant");
		LOCATIONS.put("Denorios belt", "Bajoran system");
		LOCATIONS.put("Galactic core", "Milky Way Galaxy");
		LOCATIONS.put("Glintara sector", "Beta Quadrant");
		LOCATIONS.put("Internment Camp 371", "Gamma Quadrant");
		LOCATIONS.put("Krenim space", "Grid 005");
		LOCATIONS.put("Kokytos", "Theta Helios");
		LOCATIONS.put("Moab sector", "Federation space");
		LOCATIONS.put("Muratas Star Cluster", "Delphic Expanse");
		LOCATIONS.put("Neutral zone", "Alpha Quadrant");
		LOCATIONS.put("Nu Scorpii", "Milky Way Galaxy");
		LOCATIONS.put("Orassin distortion field", "Delphic Expanse");
		LOCATIONS.put("Orion Nebula", "Orion (constellation)");
		LOCATIONS.put("Pleiades Cluster", "Alpha Quadrant");
		LOCATIONS.put("Romulan Neutral Zone", "Beta Quadrant");
		LOCATIONS.put("Sector 31", "Beta Quadrant");
		LOCATIONS.put("Sector 45", "Beta Quadrant");
		LOCATIONS.put("Subsector 4418", "Sector 450");
		LOCATIONS.put("Subsector 4432", "Sector 450");
		LOCATIONS.put("Subsector 4534", "Sector 450");
		LOCATIONS.put("Teneebian moons", "Beta Quadrant");
		LOCATIONS.put("Tolpra", "Alpha Quadrant");
		LOCATIONS.put("Zahl territory", "Grid 005");
	}

	private final AstronomicalObjectRepository astronomicalObjectRepository;

	@Override
	public FixedValueHolder<AstronomicalObject> getSearchedValue(AstronomicalObject astronomicalObject) {
		String key = astronomicalObject.getName();
		if (LOCATIONS.containsKey(key)) {
			final Optional<AstronomicalObject> locationOptional = astronomicalObjectRepository
					.findByPageTitleAndPageMediaWikiSource(LOCATIONS.get(key), MediaWikiSource.MEMORY_ALPHA_EN);
			if (locationOptional.isPresent()) {
				return FixedValueHolder.found(locationOptional.get());
			}
		}
		return FixedValueHolder.notFound();
	}
}
