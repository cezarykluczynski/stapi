package com.cezarykluczynski.stapi.etl.template.planet.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.planet.dto.PlanetTemplate;
import com.cezarykluczynski.stapi.etl.template.planet.dto.enums.AstronomicalObjectType;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AstronomicalObjectBestPickEnrichingProcessor
		implements ItemEnrichingProcessor<EnrichablePair<Pair<AstronomicalObjectType, AstronomicalObjectType>, PlanetTemplate>> {

	private static final String MARKING_OBJECT_TEMPLATE_STRING = "Marking object \"{}\" as \"{}\" from values \"{}\", \"{}\"";

	@Override
	public void enrich(EnrichablePair<Pair<AstronomicalObjectType, AstronomicalObjectType>, PlanetTemplate> enrichablePair) throws Exception {
		Pair<AstronomicalObjectType, AstronomicalObjectType> astronomicalObjectTypePair = enrichablePair.getInput();
		PlanetTemplate planetTemplate = enrichablePair.getOutput();
		planetTemplate.setAstronomicalObjectType(decideAstronomicalObjectType(planetTemplate, astronomicalObjectTypePair.getLeft(),
				astronomicalObjectTypePair.getRight()));
	}

	@SuppressWarnings({"NPathComplexity", "ReturnCount"})
	private AstronomicalObjectType decideAstronomicalObjectType(PlanetTemplate planetTemplate,
			AstronomicalObjectType currentAstronomicalObjectType, AstronomicalObjectType astronomicalObjectTypeFromProcessor) {
		if (astronomicalObjectTypeFromProcessor != null && currentAstronomicalObjectType == null) {
			return astronomicalObjectTypeFromProcessor;
		} else if (astronomicalObjectTypeFromProcessor == null && currentAstronomicalObjectType != null) {
			return currentAstronomicalObjectType;
		} else if (astronomicalObjectTypeFromProcessor == null) {
			log.warn("Both astronomical object types for planet \"{}\" were null", planetTemplate.getName());
			return null;
		} else if (astronomicalObjectTypeFromProcessor == currentAstronomicalObjectType) {
			return astronomicalObjectTypeFromProcessor;
		}

		if (!AstronomicalObjectType.PLANET.equals(currentAstronomicalObjectType)) {
			if (AstronomicalObjectType.MOON.equals(currentAstronomicalObjectType)
					&& AstronomicalObjectType.M_CLASS_PLANET.equals(astronomicalObjectTypeFromProcessor)) {
				log.info(MARKING_OBJECT_TEMPLATE_STRING, planetTemplate.getName(), AstronomicalObjectType.M_CLASS_MOON,
						currentAstronomicalObjectType, astronomicalObjectTypeFromProcessor);
				return AstronomicalObjectType.M_CLASS_MOON;
			}

			if (AstronomicalObjectType.PLANETOID.equals(currentAstronomicalObjectType)
					&& AstronomicalObjectType.D_CLASS_PLANET.equals(astronomicalObjectTypeFromProcessor)) {
				log.info(MARKING_OBJECT_TEMPLATE_STRING, planetTemplate.getName(),
						AstronomicalObjectType.D_CLASS_PLANETOID, currentAstronomicalObjectType, astronomicalObjectTypeFromProcessor);
				return AstronomicalObjectType.D_CLASS_PLANETOID;
			}

			if (AstronomicalObjectType.MOON.equals(currentAstronomicalObjectType)
					&& AstronomicalObjectType.ASTEROID.equals(astronomicalObjectTypeFromProcessor)) {
				log.info(MARKING_OBJECT_TEMPLATE_STRING, planetTemplate.getName(),
						AstronomicalObjectType.ASTEROIDAL_MOON, currentAstronomicalObjectType, astronomicalObjectTypeFromProcessor);
				return AstronomicalObjectType.ASTEROIDAL_MOON;
			}
		}

		if (Lists.newArrayList(AstronomicalObjectType.PLANET, AstronomicalObjectType.MOON).contains(astronomicalObjectTypeFromProcessor)) {
			String astronomicalObjectTypeFromProcessorName = astronomicalObjectTypeFromProcessor.name();
			String currentAstronomicalObjectTypeName = currentAstronomicalObjectType.name();
			if (currentAstronomicalObjectTypeName.endsWith(astronomicalObjectTypeFromProcessorName)) {
				log.info("For object \"{}\", given \"{}\" and \"{}\" to choose from, \"{}\" was chosen as more detailed",
						planetTemplate.getName(), currentAstronomicalObjectType, astronomicalObjectTypeFromProcessor, currentAstronomicalObjectType);
				return currentAstronomicalObjectType;
			}
		}

		log.info("For object \"{}\", given \"{}\" and \"{}\", going with \"{}\" in lack of better option", planetTemplate.getName(),
				astronomicalObjectTypeFromProcessor, currentAstronomicalObjectType, astronomicalObjectTypeFromProcessor);
		return astronomicalObjectTypeFromProcessor;
	}

}
