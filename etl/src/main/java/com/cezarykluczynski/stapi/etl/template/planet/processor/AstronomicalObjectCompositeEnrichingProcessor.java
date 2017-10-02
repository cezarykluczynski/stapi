package com.cezarykluczynski.stapi.etl.template.planet.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.planet.dto.PlanetTemplate;
import com.cezarykluczynski.stapi.etl.template.planet.dto.enums.AstronomicalObjectType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AstronomicalObjectCompositeEnrichingProcessor
		implements ItemEnrichingProcessor<EnrichablePair<Pair<AstronomicalObjectType, AstronomicalObjectType>, PlanetTemplate>> {

	@Override
	public void enrich(EnrichablePair<Pair<AstronomicalObjectType, AstronomicalObjectType>, PlanetTemplate> enrichablePair) throws Exception {
		Pair<AstronomicalObjectType, AstronomicalObjectType> astronomicalObjectTypePair = enrichablePair.getInput();
		PlanetTemplate planetTemplate = enrichablePair.getOutput();
		planetTemplate.setAstronomicalObjectType(decideAstronomicalObjectType(planetTemplate, astronomicalObjectTypePair.getLeft(),
				astronomicalObjectTypePair.getRight()));
	}

	private AstronomicalObjectType decideAstronomicalObjectType(PlanetTemplate planetTemplate,
			AstronomicalObjectType currentAstronomicalObjectType, AstronomicalObjectType astronomicalObjectTypeFromProcessor) {
		if (astronomicalObjectTypeFromProcessor != null && currentAstronomicalObjectType != null
				&& currentAstronomicalObjectType != AstronomicalObjectType.PLANET
				&& !astronomicalObjectTypeFromProcessor.equals(currentAstronomicalObjectType)) {
			if (AstronomicalObjectType.MOON.equals(currentAstronomicalObjectType)
					&& AstronomicalObjectType.M_CLASS_PLANET.equals(astronomicalObjectTypeFromProcessor)) {
				return AstronomicalObjectType.M_CLASS_MOON;
			}

			if (AstronomicalObjectType.PLANETOID.equals(currentAstronomicalObjectType)
					&& AstronomicalObjectType.D_CLASS_PLANET.equals(astronomicalObjectTypeFromProcessor)) {
				return AstronomicalObjectType.D_CLASS_PLANETOID;
			}

			if (AstronomicalObjectType.MOON.equals(currentAstronomicalObjectType)
					&& AstronomicalObjectType.ASTEROID.equals(astronomicalObjectTypeFromProcessor)) {
				return AstronomicalObjectType.ASTEROIDAL_MOON;
			}

			log.warn("Planet template \"{}\" type \"{}\" differs from \"{}\"", planetTemplate.getName(), currentAstronomicalObjectType,
					astronomicalObjectTypeFromProcessor);
		}

		return astronomicalObjectTypeFromProcessor != null ? astronomicalObjectTypeFromProcessor : currentAstronomicalObjectType;
	}

}
