package com.cezarykluczynski.stapi.etl.spacecraft.creation.processor;

import com.cezarykluczynski.stapi.etl.template.starship.dto.StarshipTemplate;
import com.cezarykluczynski.stapi.model.spacecraft.entity.Spacecraft;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class StarshipTemplateProcessor implements ItemProcessor<StarshipTemplate, Spacecraft> {

	@Override
	public Spacecraft process(StarshipTemplate item) throws Exception {
		// TODO
		return null;
	}

}
