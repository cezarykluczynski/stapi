package com.cezarykluczynski.stapi.etl.template.planet.processor;

import com.cezarykluczynski.stapi.etl.template.planet.dto.enums.AstronomicalObjectType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AstronomicalObjectTypeProcessor implements ItemProcessor<String, AstronomicalObjectType> {

	private static final String ARTIFICIAL = "Artificial";
	private static final String ASTEROID = "Asteroid";
	private static final String M = "M";

	private final AstronomicalObjectWikitextProcessor astronomicalObjectWikitextProcessor;

	public AstronomicalObjectTypeProcessor(AstronomicalObjectWikitextProcessor astronomicalObjectWikitextProcessor) {
		this.astronomicalObjectWikitextProcessor = astronomicalObjectWikitextProcessor;
	}

	@Override
	public AstronomicalObjectType process(String item) throws Exception {
		if (item == null) {
			return null;
		} else if (ARTIFICIAL.equals(item)) {
			return AstronomicalObjectType.ARTIFICIAL_PLANET;
		} else if (ASTEROID.equals(item)) {
			return AstronomicalObjectType.ASTEROID;
		} else if (M.equals(item)) {
			return AstronomicalObjectType.M_CLASS_PLANET;
		}

		return astronomicalObjectWikitextProcessor.process(item);
	}

}
